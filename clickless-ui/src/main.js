import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'
import clickLessUi from 'click-less-ui'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/clickless.scss' // clickless css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' //directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/clickless";
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar"
// 富文本组件
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'

// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

// 全局组件挂载
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
DictData.install()

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false


import {hasPermiFunc} from "@/directive/permission/hasPermiFunc.js"
// 通用接口引入
import {
  metaFields,
  getUserFields,
  setUserFields,
  fieldFilteredData, // 废弃
  fieldData,
  getData,
  exportFile
} from '@/api/clickLess-config.js'

// 通用配置引入
import {
  customRender,
  getHeaderInfoAfter,
  setUserTableHeadBefor,
  getUserTableHeadAfter,
  // getfilterTableSelectBefor,
  getFilterTableSelectAfter,
  getPageDataBefor,
  getPageDataAfter,
  //
  getDict,
  clearParams

} from './clickLess-ui-config.js'
// 插入clickLessUi及扩展dx组件
Vue.use(clickLessUi, {
  tableSearch:false,//全局表格模糊搜索
  tableToolConfig:{ColumnContrl:true},//全局字段选择器控制配置
  showFilterButton:true, //右上角清空筛选按钮
	isSingleHeaderFilter:true, // 表格头部筛选 单选控制
  cacheFilterOptions:false, //表头筛选项不进行缓存
  // 自定义渲染
  customRender: customRender,
  // 表格头部
  getHeaderInfo: metaFields,
  getHeaderInfoAfter: getHeaderInfoAfter,
  // 用户配置 保存、修改
  getUserTableHead: getUserFields,
  setUserTableHead: setUserFields,
  setUserTableHeadBefor: setUserTableHeadBefor,
  getUserTableHeadAfter: getUserTableHeadAfter,
  // 表头筛选
  // filterTableSelect: fieldData,
  // getfilterTableSelectBefor: getfilterTableSelectBefor,
  getfilterTableSelectAfter: getFilterTableSelectAfter,
  // page表格数据
  // getPageData: getData,
  // getPageDataBefor: getPageDataBefor,
  // getPageDataAfter: getPageDataAfter
})

Vue.prototype.$HasPermiFunc = hasPermiFunc
//查询字典函数
Vue.prototype.$GetDict = getDict
//get形式需清理数组和json类型参数
Vue.prototype.$ClearParams= clearParams

let VueApp = new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

window.VueApp = VueApp
