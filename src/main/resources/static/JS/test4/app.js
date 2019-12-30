
new Vue({
    el: "#app",
    data: { // data 객체
        mode: "list", // 상태 표시
        memo: {
            id: null,
            title: null,
            regDate: null
        },
        Pmemos: [],
        Bmemos: [],
        category : "Personal",
        categories : ["Personal", "Business"],
        createPromise : {
            "promiseName" : '',
            "roomHostId" : '',
            "placeName" : '',
            "placeX" : '',
            "placeY" : '',
            "promiseTime" : "2019-12-27 11:43:19.0"
        }
    },
    methods: { // methods 객체
        renew: function(val) {
            return JSON.parse(JSON.stringify(val)); // JSON.stringify()는 값을 JSON 표기법으로 변환
        },

        Popen: function(title) {
            for(var i in this.Pmemos) { // id를 가진 memos 찾기
                if(this.Pmemos[i].title === title) { // id가 같은 memos 찾기
                    this.memo = this.renew(this.Pmemos[i]); // 자바 스크립트에서 = 의 의미는 객체 일때 단순히 내용을 넣어주는것 뿐만 아니라 참조값으로 들어간다 
                    break; //그래서 Prenew 값만 들어가게 변환해야 한다
                }
            }
            this.mode = "edit";
        },

        Pwrite: function() { // 함수 method properties
            this.mode = "write";
            this.memo = {
                id: null,
                title: null,
                regDate: null
            }; // 초기화
        },

        Psave: function() {
            let id = this.Pmemos.length + 1;

            if(this.mode === "write"){
                this.Pmemos.push({ // 객체 push
                    id: id,
                    title: this.memo.title,
                    regDate: new Date()
                }); // 내용추가
            } else if(this.mode === "edit"){
                for(var i in this.Pmemos){
                    if(this.Pmemos[i].id === this.memo.id){
                        this.Pmemos[i] = this.renew(this.memo);
                        break;
                    }
                } // 내용 수정 
            }

            this.mode = "list";
            localStorage.setItem("Pmemos", JSON.stringify(this.Pmemos)); // 브라우저에 저장 chrome -> application -> localstorage -> file
        },

        Premove: function() {
            if(confirm("정말 삭제하시겠습니까?")){
                for(var i in this.Pmemos){
                    if(this.Pmemos[i].id === this.memo.id){
                        this.Pmemos.splice(i, 1); // 배열 변경 감지, 삭제
                        break;
                    }
                }

                this.mode = "list";
                localStorage.setItem("Pmemos", JSON.stringify(this.Pmemos));
            }
        },

        Bopen: function(id) {
            for(var i in this.Bmemos) { // id를 가진 memos 찾기
                if(this.Bmemos[i].title === id) { // id가 같은 memos 찾기
                    this.memo = this.renew(this.Bmemos[i]); // 자바 스크립트에서 = 의 의미는 객체 일때 단순히 내용을 넣어주는것 뿐만 아니라 참조값으로 들어간다 
                    break; //그래서 renew로 값만 들어가게 변환해야 한다
                }
            }
            this.mode = "edit";
        },

        Bwrite: function() { // 함수 method properties
            this.mode = "write";
            this.memo = {
                id: null,
                title: null,
                regDate: null
            }; // 초기화
        },

        Bsave: function() {
            let id = this.Bmemos.length + 1;

            if(this.mode === "write"){
                this.Bmemos.push({ // 객체 push
                    id: id,
                    title: this.memo.title,
                    regDate: new Date()
                }); // 내용추가
            } else if(this.mode === "edit"){
                for(var i in this.Bmemos){
                    if(this.Bmemos[i].id === this.memo.id){
                        this.Bmemos[i] = this.renew(this.memo);
                        break;
                    }
                } // 내용 수정 
            }

            this.mode = "list";
            sessionStorage.setItem("Bmemos", JSON.stringify(this.Bmemos)); // 브라우저에 저장 chrome -> application -> localstorage -> file
        },

        Bremove: function() {
            if(confirm("정말 삭제하시겠습니까?")){
                for(var i in this.Bmemos){
                    if(this.Bmemos[i].id === this.memo.id){
                        this.Bmemos.splice(i, 1); // 배열 변경 감지, 삭제
                        break;
                    }
                }

                this.mode = "list";
                sessionStorage.setItem("Bmemos", JSON.stringify(this.Bmemos));
            }
        },
        printInfo: function(query){
            this.createPromise.roomHostId=query.id;
            console.log(this.createPromise)
        }
    },

    created: function() { // vue.js가 가지고 있는 기본 메소드, 앱이 처음 생성될때 실행 되는 부분
        let Pmemos = localStorage.getItem("Pmemos");
        let Bmemos = sessionStorage.getItem("Bmemos");
        if(Pmemos) { // 존재 여부
            this.Pmemos = JSON.parse(Pmemos);
        } else {
            this.Bmemos = JSON.parse(Bmemos);
        }
    }
});