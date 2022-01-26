FROM nginx:1.21.5-alpine

# 后端接口地址
ENV BACKEND_URL=""

# 将前端编译后文件 复制到容器内nginx指定目录下
COPY ./dist/ /usr/share/nginx/html/
# 将nginx配置模板 复制到容器根目录
COPY ./nginx.conf.template /

# 1.替换修改配置文件中的变量BACKEND_URL
# 2.将修改后的配置文件替换nginx默认default.conf配置文件
# 3.关闭nginx后台运行
CMD envsubst '${BACKEND_URL}' < ./nginx.conf.template > /etc/nginx/conf.d/default.conf \
    && cat /etc/nginx/conf.d/default.conf \
    && nginx -g 'daemon off;'
