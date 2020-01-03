new Vue({
    el: "#app",
    data: { // data 객체
        mode: "list", // 상태 표시
        memo: {
            id: null,
            title: null,
            regDate: null
        },
        memos: []
    },
    methods: { // methods 객체
        renew: function(val) {
            return JSON.parse(JSON.stringify(val)); // JSON.stringify()는 값을 JSON 표기법으로 변환
        },

        open: function(id) {
            for(var i in this.memos) { // id를 가진 memos 찾기
                if(this.memos[i].id === id) { // id가 같은 memos 찾기
                    this.memo = this.renew(this.memos[i]); // 자바 스크립트에서 = 의 의미는 객체 일때 단순히 내용을 넣어주는것 뿐만 아니라 참조값으로 들어간다 
                    break; //그래서 renew로 값만 들어가게 변환해야 한다
                }
            }
            this.mode = "edit";
        },

        write: function() { // 함수 method properties
            this.mode = "write";
            this.memo = {
                id: null,
                title: null,
                regDate: null
            }; // 초기화
        },

        save: function() {
            let id = this.memos.length + 1;

            if(this.mode === "write"){
                this.memos.push({ // 객체 push
                    id: id,
                    title: this.memo.title,
                    regDate: new Date()
                }); // 내용추가
            } else if(this.mode === "edit"){
                for(var i in this.memos){
                    if(this.memos[i].id === this.memo.id){
                        this.memos[i] = this.renew(this.memo);
                        break;
                    }
                } // 내용 수정 
            }

            this.mode = "list";
            localStorage.setItem("memos", JSON.stringify(this.memos)); // 브라우저에 저장 chrome -> application -> localstorage -> file
        },

        remove: function() {
            if(confirm("정말 삭제하시겠습니까?")){
                for(var i in this.memos){
                    if(this.memos[i].id === this.memo.id){
                        this.memos.splice(i, 1); // 배열 변경 감지, 삭제
                        break;
                    }
                }

                this.mode = "list";
                localStorage.setItem("memos", JSON.stringify(this.memos));
            }
        }
    },

    created: function() { // vue.js가 가지고 있는 기본 메소드, 앱이 처음 생성될때 실행 되는 부분
        let memos = localStorage.getItem("memos");
        if(memos) { // 존재 여부
            this.memos = JSON.parse(memos);
        }
    }
});