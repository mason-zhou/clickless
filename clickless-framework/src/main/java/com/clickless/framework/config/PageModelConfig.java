package com.clickless.framework.config;

import com.clickless.common.annotation.pagemodel.PageModelField;
import com.clickless.common.annotation.pagemodel.PageModel;
import com.clickless.common.core.domain.pagemodel.PageModelInfo;
import com.clickless.common.core.domain.pagemodel.PageModelFieldInfo;
import com.clickless.common.core.pagemodel.PageModelRegistry;
import com.clickless.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 页面模型配置类
 * <p>
 * description
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/24
 */
@Configuration
public class PageModelConfig {
    @Autowired
    private Environment env;

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private static final String SCAN_PACKAGES = "pageModel.scanPackages";

    /**
     * 实例化模型注册中心
     *
     * @return
     */
    @Bean
    public PageModelRegistry pageModelRegistry() {
        PageModelRegistry registry = new PageModelRegistry();
        // 通过扫描路径，将模型注册到注册中心上
        String packagesToScan = env.getProperty(SCAN_PACKAGES);
        List<PageModelInfo> pageModelList = handle(packagesToScan);
        for (PageModelInfo pageModelInfo : pageModelList) {
            registry.register(pageModelInfo.getKey(), pageModelInfo);
        }
        return registry;
    }

    /**
     * 扫描包路径 获取模型信息
     *
     * @param packagesToScan
     * @return
     */
    public static List<PageModelInfo> handle(String packagesToScan) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        List<PageModelInfo> allResult = new ArrayList<>();
        try {
            for (String aliasesPackage : packagesToScan.split(",")) {
                aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/" + DEFAULT_RESOURCE_PATTERN;
                Resource[] resources = resolver.getResources(aliasesPackage);
                if (resources != null && resources.length > 0) {
                    MetadataReader metadataReader = null;
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            try {
                                String className = metadataReader.getClassMetadata().getClassName();
                                Class<?> clazz = Class.forName(className);
                                // 判断是否是列表模型，即是否存在模型注解
                                boolean isPageModel = clazz.isAnnotationPresent(PageModel.class);
                                if (isPageModel) {
                                    PageModel classAnnotation = clazz.getAnnotation(PageModel.class);

                                    PageModelInfo pageModelInfo = new PageModelInfo();
                                    // 如果注解上没有指定模型key值，则以类名作为key值
                                    pageModelInfo.setKey(StringUtils.isBlank(classAnnotation.key()) ? clazz.getSimpleName() : classAnnotation.key());
                                    pageModelInfo.setName(classAnnotation.name());
                                    pageModelInfo.setDbTableName(classAnnotation.dbTableName());

                                    // 找到所有标注了注解的字段 并按照注解上的order属性进行排序
                                    List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                                            .filter(field -> field.isAnnotationPresent(PageModelField.class))
                                            .sorted(Comparator.comparingInt(field -> field.getAnnotation(PageModelField.class).sort()))
                                            .collect(Collectors.toList());

                                    if (CollectionUtils.isNotEmpty(fields)) {
                                        List<PageModelFieldInfo> pageModelFieldInfoList = new ArrayList<>();
                                        for (Field f : fields) {
                                            PageModelField fieldAnnotation = f.getAnnotation(PageModelField.class);
                                            PageModelFieldInfo pageModelFieldInfo = new PageModelFieldInfo();
                                            // 如果注解上没有指定字段key值，则以类字段名作为key值
                                            pageModelFieldInfo.setKey(StringUtils.isBlank(fieldAnnotation.key()) ? f.getName() : fieldAnnotation.key());
                                            pageModelFieldInfo.setName(fieldAnnotation.name());
                                            pageModelFieldInfo.setDbColumnName(fieldAnnotation.dbColumnName());
                                            pageModelFieldInfo.setDictType(fieldAnnotation.dictType());
                                            pageModelFieldInfoList.add(pageModelFieldInfo);
                                        }
                                        pageModelInfo.setFields(pageModelFieldInfoList);
                                    }
                                    allResult.add(pageModelInfo);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allResult;
    }


}
