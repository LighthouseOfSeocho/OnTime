let vue = new Vue({
    el: "#app",
    data: { // data 객체
        mode: "list", // 상태 표시,
        user: null,
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
            "promiseName" : "",
            "roomHostId" : "",
            "placeName" : "",
            "placeX" : "",
            "placeY" : "",
            "address" : "",
            "promiseDate" : "",
            "promiseTime" : "",
            "promiseHour" : "",
            "promiseMinute" : ""
        },
        searchedPlaces : null,
        promises : "",
        selectedPromise : null,
        hour : ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"],
        minute : ["00","10","20","30","40","50"]
    },
    methods: { // methods 객체
        range: function(start,end){
            let result = [];
            for(let i = start ; i<end ; i++){
                result.push(i);
            }
            return result;
        },
        setPlace: function(place){
            this.createPromise.placeName=place.place_name
            this.createPromise.placeX=place.x
            this.createPromise.placeY=place.y
        },
        selectPromise: function(promise){
            console.log("Yeaahhhhh")
            this.selectedPromise=promise
            this.mode = "promise"
        },
        renew: function(val) {
            return JSON.parse(JSON.stringify(val)); // JSON.stringify()는 값을 JSON 표기법으로 변환
        },

        Popen: function(promiseName) {
        	console.log(6666666666,promises)
        	console.log(111111111111, this.mode);
            for(var i in this.promises) { // id를 가진 memos 찾기
            	console.log(1231231231, i)
                if(this.promises[i].promiseName === promiseName) { // id가 같은 memos 찾기
                    this.promises = this.renew(this.promises[i]); // 자바 스크립트에서 = 의 의미는 객체 일때 단순히 내용을 넣어주는것 뿐만 아니라 참조값으로 들어간다 
                    break; //그래서 Prenew 값만 들어가게 변환해야 한다
                }
            }
            this.mode = "edit";
            console.log(11111111,this.promises);
            console.log(2222222, this.mode);
        },

        Pwrite: function() { // 함수 method properties
            this.mode = "write";
            this.promises = {
                "promiseName" : "",
                "roomHostId" : "",
                "placeName" : "",
                "placeX" : "",
                "placeY" : "",
                "promiseTime" : "2019-12-27 11:43:19.0"
            }; // 초기화
            console.log(22222222222,this.promises)
        },

        Psave: function() {
            let id = this.promises.length + 1;

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
                console.log(3333333,this.promises)
            }

            this.mode = "list";
            localStorage.setItem("Pmemos", JSON.stringify(this.Pmemos)); // 브라우저에 저장 chrome -> application -> localstorage -> file
        },

        Premove: function() {
        	console.log(5555555,this.promises)
            if(confirm("정말 삭제하시겠습니까?")){
                for(var i in this.promises){
                    if(this.promises[i].promiseName === this.promises.promiseName){
                        this.promises.splice(i, 1); // 배열 변경 감지, 삭제
                        break;
                    }
                }
                console.log(4444444444,this.promises)

                this.mode = "list";
                localStorage.setItem("promises", JSON.stringify(this.promises));
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
        create: function(){
            this.createPromise.roomHostId = this.user.id;
            axios.post("/promise", {
                promiseName : this.createPromise.promiseName,
                roomHostId : this.createPromise.roomHostId,
                placeName : this.createPromise.placeName,
                placeX : this.createPromise.placeX,
                placeY : this.createPromise.placeY,
                address : this.createPromise.address,
                promiseTime : this.createPromise.promiseDate+" "+this.createPromise.promiseHour+":"+this.createPromise.promiseMinute+":00.0"
            })
                .then(res=>{
                    if(res.data){
                        alert("약속이 생성되었습니다.");
                        axios.get("/promise", {params:{userId:this.user.id}})
                            .then(res=>{
                                this.promises = res.data;
                            })
                            .catch(e=>{
                                console.log(e);
                            })
                    } else {
                        alert("오류 발생");
                    }
                }).catch(e=>{
                    alert(e);
                });
        },
        getLocation: function() {
            if (navigator.geolocation) { // GPS를 지원하면
              navigator.geolocation.getCurrentPosition(function(position) {
                alert(position.coords.latitude + ' ' + position.coords.longitude);
              }, function(error) {
                console.error(error);
              }, {
                enableHighAccuracy: false,
                maximumAge: 0,
                timeout: Infinity
              });
            } else {
              alert('GPS를 지원하지 않습니다');
            }
          }
    },

    created: function() { // vue.js가 가지고 있는 기본 메소드, 앱이 처음 생성될때 실행 되는 부분
        this.user = query;

        let Pmemos = localStorage.getItem("Pmemos");
        let Bmemos = sessionStorage.getItem("Bmemos");
        if(Pmemos) { // 존재 여부
            this.Pmemos = JSON.parse(Pmemos);
        } else {
            this.Bmemos = JSON.parse(Bmemos);
        }
        axios.get("/promise", {params:{userId:this.user.id}})
            .then(res=>{
                this.promises = res.data;
            })
            .catch(e=>{
                console.log(e);
            })
    }
    
});