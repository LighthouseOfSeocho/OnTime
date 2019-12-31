<template>
  <div id="promise-list" v-bind="promises">
    ASDFASDFASDFASF <br>
    <ul>
        <li v-for="promise in promises">
            <div class="prom" @click="$router.push({name:'promiseDetail',params:{promise:promise}})">
                {{promise.promiseName}}<br>
                {{promise.placeName}}<br>
                {{promise.promiseTime}}
            </div>
        </li>
    </ul>
  </div>
</template>

<script>
import Promise from './promise/Promise.vue'
export default {
    methods:{
        getPromise(){
            return this.$http.get("http://localhost:9000/promise",{
                headers: {
                    'Content-Type': 'application/json'
                },
                params : {
                    "userId" : "choi"
                }
            })
            .then( res =>{
                this.promises = res.data
            }).catch(e =>{
                this.promises = e
            })
        }
    },
    data (){
        return {
            promises : []
        }
    },
    created(){
        this.getPromise()
    }
}
</script>

<style>
  div.prom {
      border: 2px solid black;
      border-radius: 5px;
      margin : 10px;
  }
  li {
    list-style-type: none;
 }
</style>