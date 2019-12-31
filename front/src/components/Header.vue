<template>
  <div id="header">
    <nav>
      <router-link to="/login" tag="button"><b>로그인</b></router-link>
      <router-link to="/my-promise" tag="button"><b>내 약속</b></router-link>
    </nav>
  </div>
</template>

<script>
export default {
}
</script>

<style>
  nav > button {
    border: 1px solid skyblue; 
    background-color: rgba(0,0,0,0); 
    color: skyblue; padding: 5px;
    border-radius: 5px
  }
</style>