import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export const state = () => ({
  websocketUrl: "http://localhost:9000/ws",
  connected: false
});

export const getters = {
  connected: state => state.connected
};

export const mutations = {
  setConnected(state, status) {
    state.connected = status;
  }
};

export const actions = {
  connect({ state, commit }) {
    if (state.connected) return;
    this.socket = new SockJS(state.websocketUrl);
    this.stompClient = Stomp.over(this.socket);
    // comment the line below if you want to see debug messages
    this.stompClient.debug = msg => {};
    this.stompClient.connect(
      {},
      frame => {
        commit("setConnected", true);
        this.stompClient.subscribe("/app/chat/roomList", tick => {  // 유저 등록시 현존하는 모든 roomList를 받아오는 듯?
          const roomList = JSON.parse(tick.body);
          commit("main/initRoom", roomList, { root: true });
        });
        // subscribe new rooms
        this.stompClient.subscribe("/chat/newRoom", tick => {       // 새로운 방에 등록
          const room = JSON.parse(tick.body);
          commit("main/addRoom", room, { root: true });
        });
        // test
        const subscribeUrl = "/chat/" + roomId + "/userList";
        this.stompClient.subscribe(
          subscribeUrl,
          tick => {
            const roomUserList = JSON.parse(tick.body);
            commit("updateRoomUserList", roomUserList);
          },
          { id: roomId + "_userList" }
        );
        this.stompClient.subscribe(
          "/chat/" + roomId + "/messages",
          tick => {
            const message = JSON.parse(tick.body);
            const roomMessage = { roomKey: roomId, message: message };
            commit("sendMessage", roomMessage);
          },
          { id: roomId + "_messages" }
        );
      },
      error => {
        console.log(error);
        commit("setConnected", false);
      }
    );
  },
  subscribeRoomList() {}
};
