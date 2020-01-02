<template>
  <div id="promise">
    <h1>{{promise.promiseName}}</h1>
    장소 : {{promise.placeName}}<br>
    시간 : {{promise.promiseTime}}<br>
    멤버 : {{getMembers()}}
  </div>
</template>

<script>
export default {
  methods : {
    getParams(){
      this.promise=this.$route.params.promise
      this.$http.get("http://localhost:9000/promise/members",{
                headers: {
                    'Content-Type': 'application/json'
                },
                params : {
                    "promiseId" : this.$route.params.promise.id
                }
      })
      .then(res=>{
        this.members = res.data
      })
   },
    getMembers(){
      let member = ""
      this.members.forEach(v=>{
        member+=v.userName+"  "
      })
      return member
    }
  },
  data(){
    return {
      promise : null,
      members : []
    }
  },
  created(){
    this.getParams()
  }
}
</script>