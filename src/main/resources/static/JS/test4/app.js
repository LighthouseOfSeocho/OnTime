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
        favorite: {
            place: null,
            friend: null
        },
        hour : ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"],
        minute : ["00","10","20","30","40","50"]
    },
    methods: { // methods 객체
        setPlace: function(place){
            this.createPromise.placeName=place.place_name
            this.createPromise.placeX=place.x
            this.createPromise.placeY=place.y
            this.createPromise.address=place.road_address_name
        },
        setModeMyPage: function(){
            this.mode='myPage';
            let place = [];
            let friend = [];
            this.promises.forEach(promise=>{
                let temp = promise.address.split(' ');
                if(promise.address.substring(0,2)=="서울"){
                    place.push(temp[1])
                }else{
                    place.push(temp[0])
                }
                promise.members.slice(1).forEach(member=>{
                    friend.push(member.userName)
                })
            })
            this.favorite.friend = this.count(friend);
            this.favorite.place = this.count(place);
        },
        selectPromise: function(promise){
            this.selectedPromise=promise;
            this.mode="promise";
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
          },
        updateUser: function(){
            axios.put("/user", {
                id:this.user.userId,
                userEmail:this.user.userEmail,
                userName:this.user.userName,
                userGender:this.user.userGender,
                userAge:this.user.userAge,
                userAccount:this.user.userAccount,
                userBirthday:this.user.userBirthday,
                userPhone:this.user.userPhone
            })
                .then(res=>{
                    alert('수정되었습니다.')
                })
                .catch(e=>{
                    alert(e)
                })
        },
        getMembers: function(promise){
            axios.get("/promise/members", {params:{promiseId:promise.id}})
                .then(res=>{
                    promise.members= res.data;
                })
                .catch(e=>{
                    console.log(e)
                })
        },
        count: function(group){
            let temp = group.reduce(function(map,el){
                map[el] = (map[el]||0)+1;
                return map;
            }, Object.create(null));
            let keys = Object.keys(temp);
            return keys.sort(function(a, b) {
                if(temp[a]>temp[b]){
                    return -1;
                }else if(temp[a]<temp[b]){
                    return 1;
                }else{
                    return 0;
                }
            }).slice(0,5);
        },
        logout: function(){
            axios.get("/logout")
                .then(res=>{
                	if(res.data){
                		this.user=null;
                		alert('로그아웃 되었습니다');
                		window.location.href = 'http://192.168.2.104:9000/';            		
                	}
                })
                .catch(e=>{
                    alert('로그아웃 실패')
                })
        }
    },

    created: function() { // vue.js가 가지고 있는 기본 메소드, 앱이 처음 생성될때 실행 되는 부분
        this.user = query;
        this.user.userId = query.id;
        axios.get("/promise", {params:{userId:this.user.userId}})
            .then(res=>{
                this.promises = res.data;
                res.data.forEach(promise=>{
                    this.getMembers(promise);
                })
            })
            .catch(e=>{
                console.log(e);
            })
    }
});