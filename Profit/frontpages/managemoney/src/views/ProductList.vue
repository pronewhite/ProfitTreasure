<template>
  <div>
    <Header></Header>
    <div class="content clearfix">
      <!--排行榜-->
      <ul class="rank-list">
        <li v-for="(item,index) in rank" :key = "item.id">
          <img src="@/assets/image/list-rank1.png" alt="" v-if="index == 0" >
          <img src="@/assets/image/list-rank2.png" alt="" v-if="index == 1" >
          <img src="@/assets/image/list-rank3.png" alt="" v-if="index == 2" >
          <p class="rank-list-phone">{{item.phone}}</p>
          <span>{{item.money}}元</span>
        </li>
      </ul>
      <!--产品列表-->
      <ul class="preferred-select clearfix">
        <li v-for="product in productList" :key ="product.id">
          <h3 class="preferred-select-title">
            <span>{{product.productName}}</span>
            <img src="@/assets/image/1-bg1.jpg" alt="">
          </h3>
          <div class="preferred-select-number">
            <p><b>{{product.rate}}</b>%</p>
            <span>历史年化收益率</span>
          </div>
          <div class="preferred-select-date">
            <div>
              <span>投资周期</span>
              <p><b>{{product.cycle}}</b>个月</p>
            </div>
            <div>
              <span>余利可投资金额</span>
              <p><b>{{product.leftProductMoney}}</b>元</p>
            </div>
          </div>
          <p class="preferred-select-txt">
            优选计划项目，投资回报周期{{product.cycle}}个月，起点低，适合短期资金周转、对流动性要求高的投资人。
          </p>
          <a href="javascript:void(0)" @click = "goLink('/page/product/detail',{productId:product.id})" class="preferred-select-btn">立即投资</a>
        </li>
      </ul>
      <!--分页-->
      <div class="page_box">
        <ul class="pagination">
          <li class="disabled"><a href="javascript:void(0)" @click = "first"><span>首页</span></a></li>
          <li><a href="javascript:void(0)" @click = "pre">上一页</a></li>
          <li class="active"><span>{{page.pageNo}}</span></li>
          <li><a href="javascript:void(0)" @click = "next">下一页</a></li>
          <li><a href="javascript:void(0)" @click = "end">尾页</a></li>
          <li class="totalPages"><span>共{{page.totaoPages}}页</span></li>
        </ul>
      </div>
    </div>
    <Button></Button>
  </div>
</template>

<script>
let pType = 0;
import Header from "@/components/Header";
import Button from "@/components/Button";
import {doGet} from "@/api/httpRequest";
import layx from "vue-layx";

export default {
  name: "ProductList",
  components:{
    // eslint-disable-next-line vue/no-unused-components
    Header,
    // eslint-disable-next-line vue/no-unused-components
    Button
  },
  data(){
    return{
      rank:[
        {
          phone: "",
          money: 0
        }
      ],
      productList:[{
        id: 0,
        productName: "",
        rate: 0.0,
        cycle: 0,
        releaseTime: "",
        productType: 0,
        productNo: "",
        productMoney: 0,
        leftProductMoney: 0,
        bidMinLimit: 0,
        bidMaxLimit: 0,
        productStatus: 0,
        productFullTime: "",
        productDesc: ""
      }],
      page:{
        pageNo: 0,
        pageSize: 0,
        totaoPages: 0,
        sumRecords: 0
      }
    }
  },
  mounted() {
    pType = this.$route.query.pType
    //发送请求获得投资排行榜前三的用户
    doGet('/v1/invest/info').then( resp =>{
      if(resp){
        this.rank = resp.data.list
      }
    })
    this.initPage(pType,1,9)
  },
  methods:{
    initPage(pType,pNo,pSize){
      //发送请求获得用户选中产品的详细信息
      doGet('/v1/product/list',{pType:pType,pageNo:pNo,pageSize:pSize}).then(resp =>{
        this.productList = resp.data.list
        this.page = resp.data.page
      })
    },
    //点击首页
    first(){
      if(this.page.pageNo == 1){
        layx.msg('已经是第一页数据.',{dialogIcon:'warn',position:'ct'});
      }else{
        this.initPage(pType,1,9)
      }
    },
    pre(){
      if(this.page.pageNo <= 1){
        layx.msg('已经是第一页数据.',{dialogIcon:'warn',position:'ct'});
      }else{
        this.initPage(pType,this.page.pageNo - 1,9 )
      }
    },
    next(){
      if(this.page.pageNo >= this.page.totaoPages){
        layx.msg('已经是最后一页数据.',{dialogIcon:'warn',position:'ct'});
      }else{
        this.initPage(pType,this.page.pageNo + 1,9 )
      }
    },
    end(){
      if(this.page.pageNo == this.page.totaoPages){
        layx.msg('已经是最后一页数据.',{dialogIcon:'warn',position:'ct'});
      }else{
        this.initPage(pType,this.page.totaoPages,9 )
      }
    },
    goLink(url,params) {
      //使用router做页面跳转， vue中的对象
      this.$router.push({
        path: url,
        query: params
      })
    }
  }
}
</script>

<style scoped>

</style>