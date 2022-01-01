import Vue from 'vue'
import store from './store'
export function  getHeaderInfoAfter(res){
      res.data.fields.forEach(item=>{
        item.fieldKey = item.key
        item.fieldLabel = item.name
        //表头筛选默认无
        //隐藏所有筛选和排序
        item.searchFlag = '1'
        item.sortable = true
        //表格列默认宽度
        item.minWidth = (item.fieldLabel.length * 28 + 14)
      })
     // console.log('res.data',res.data)
      return {
        headers:res.data.fields  //headers 固定取值字段
      }
  }

//获取用户表头控制
export function  getUserTableHeadAfter(res){
      if(!res.data || res.data.length == 0) {
        return {customTitle:null}
      }
      res.data.forEach(item=>{
        item.fieldKey = item.key
        item.fieldLabel = item.name
        item.showFlag = '1'
      })
      return  {customTitle:res.data} //customTitle 固定取值字段
}
export function  setUserTableHeadBefor(query){
      let data = VueApp.$deepCopy(query)
      // console.log('setUserTableHeadBefor',data)
      let fieldKeys= data.config.filter(item=>{
        return item.showFlag === '1'
      })
      if(fieldKeys.length == 0){
        VueApp.$message.error('至少展示一个字段')
        return  false
      }
      data.fieldKeys = fieldKeys.map(item=>{
        return item.fieldKey
      })
      delete data.config
      return data
}

//获取表格数据
export function  getPageDataBefor(el,query){
    // console.log(query)
    let  data =  VueApp.$deepCopy(query)

    //排序数据写入
    // orderByColumn=数据库列名
    // isAsc=asc|desc
    // order: "ascending"
    // prop: "productNo"
    if(data.sortData && data.sortData.length >0 ){
      //匹配出排序的字段对应的数据库字段
      let field = el.$refs.tableLayout.columnsList.filter(item=>{
        return item.fieldKey === data.sortData[0]['prop']
      })[0]
      if(field){
        data.orderByColumn = field.dbColumnName
        data.sortData[0]['order'] === "ascending" ? data.isAsc = 'asc'  :''
        data.sortData[0]['order'] === "descending" ? data.isAsc = 'desc'  :''
      }
    }
    //清理组件内部无关参数
    data = el.$ClearParams(data)
    //缓存查询参数
    el.reqQueryParams = data
    return data // this.$ClearParams(data)
}
//获取表格数据 回调
export function  getPageDataAfter(res){
  res.total=res.data.total
  return res
}
//获取表头筛选数据
export function getfilterTableSelectBefor(el,query,column){
      // console.log('query',query)
      let  data =  VueApp.$deepCopy(el.reqQueryParams)
      // orderByColumn: product_no
      // isAsc: desc
      //如果排序字段与当前字段不一致，则本次请求不附加此参数
      if(data.orderByColumn && data.orderByColumn != column.dbColumnName){
        data.orderByColumn = null
        data.isAsc = null
      }
      if(data.params){
        data.params.fieldKey = column.fieldKey
      }else{
        data.params = {fieldKey: column.fieldKey}
      }
      return data // this.$ClearParams(data)
}
export function getFilterTableSelectAfter(res,column){
      console.log(column)
      //改为生成代码接口
      let list =  res.rows || res.data
      if(list && list.rows){
        list = list.rows
      }

      //结束
      // console.log('getfilterTableSelectAfter',res)
      let arr =[]
      list.forEach(value=>{
        if(column.dict || column.dictType){
          let dict =  column.dict || column.dictType
          // console.log(dict,value,VueApp.$GetDict(dict),VueApp.$GetDict(dict,value))
          let label = null
          try{
            label = VueApp.$GetDict(dict,value)[0]
          }catch(e){
            // console.log(e)
          }
          arr.push({label: label ? label.dictLabel : value,value:value})
        }else{
          arr.push({label:value,value:value})
        }
      })
      return  {data:arr}
  }
 //获取数据字典
export function  getDictBefor(data,el){

      // data.labelType = data.dictType
      // delete data.dictType
      return  data.dictType // data
  }
export function  getDictAfter(data,el){

      let arr=data.data.map(item=>{
        return {
          dictLabel:item.labelLabel,
          dictValue:item.labelValue
        }
      })
      data.data=arr
      return  data
  }

//用户表头配置 耦合
export function  customRender(field,data){
      let value = data.row[field.fieldKey]
      if(field.dict || field.dictType){
        let dict =  field.dict || field.dictType
        let label = null
        try{
          label = VueApp.$GetDict(dict,value)[0]
        }catch(e){
          // console.log(e)
        }
        return label ? label.dictLabel : value
      }

      if(value === 0){
        return 0
      }
      return value || '--'

}
//筛选字典项
export function  getDict(key,value){
    if(!key){return []}
    if(value === null || value === undefined){return store.getters.dict ? store.getters.dict[key] : []}
    if(!store.getters.dict[key]){ console.log('未匹配到字典类型',key,value); return [] }
    return store.getters.dict[key].filter(item=>{
      return item.dictValue == value
    })
  }
  //
export function  clearParams(data){
  let res = VueApp.$deepCopy(data)
  // console.log('res-----data',data)
  //表格数据前置拦截会包含此参数
  if(res.headerFilterData && res.headerFilterData.length > 0){
    res.headerFilterData.forEach(item=>{
       res[item.fieldKey] = item.Titlefilter.join(',')
    })
  }
  //表格头部筛选获取选项时 会附加此参数
  if(res.FilterData && res.FilterData.length > 0){
    res.FilterData.forEach(item=>{
       res[item.fieldKey] = item.Titlefilter.join(',')
    })
  }
  if(res.screenData ){
     for(let key in res.screenData){
       res.screenData[key] ? res[key] = res.screenData[key] :''
     }
  }

  //
  for(let key in res){
    if(key !== 'params'){
      //清除数组格式参数
      if(Array.isArray(res[key])){
        delete res[key]
      }
      //清除数组格式参数
      if( typeof res[key] == 'object'){
        delete res[key]
      }
    }
  }
  return res
}
