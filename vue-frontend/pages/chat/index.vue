<template>
  <v-flex
    xs12
    sm5
    text-center
  >
    <p>Welcome, <b>{{ username }}</b>! Select the room or add a new one to chat with other users.</p>

    <form @submit.prevent="subscribe">
      <v-text-field
        label="Type a room key"
        type="text"
        ref="message"
        v-model="message"
      >
        <template v-slot:append>
          <v-btn
            color="primary"
            @click="subscribe"
          >
            Enter a room
          <v-icon right>keyboard_return</v-icon>
          </v-btn>
        </template>
      </v-text-field>
    </form>

  </v-flex>

</template>

<script>
export default {
  fetch({ store }) {
    store.commit("main/showSidebar", true);
    store.dispatch("websocket/connect");
  },
  layout: "chat",
  computed: {
    username() {
      return this.$store.getters["main/username"];
    }
  },
  methods: {
    subscribe() {
      const roomId = this.message;
      this.$refs["message"].reset();
      this.$store.dispatch("main/subscribeRoomUserList", roomId);
      this.$router.push("/chat/" + roomId);
    }
  }
};
</script>

<style scoped>
</style>