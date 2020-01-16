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
            promiseName : "",
            roomHostId : "",
            placeName : "",
            placeX : "",
            placeY : "",
            address : "",
            promiseDate : "",
            promiseTime : "",
            promiseHour : "",
            promiseMinute : ""
        },
        searchedPlaces : null,
        promises : "",
        selectedPromise : null,
        favorite: {
            place: null,
            friend: null
        },
        hour : ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"],
        minute : ["00","10","20","30","40","50"],
        currentTime : {
            year : new Date().getFullYear(),
            month : new Date().getMonth()+1,
            date : new Date().getDate(),
            hour : new Date().getHours(),
            minutes : new Date().getMinutes(),
        },
        position : {
            latitude:"",
            longitude:""
        },
        testId : ""
    },
    methods: { // methods 객체
        getDateTime: function(promise){
            let temp=`${this.currentTime.year}-${this.currentTime.month}-${this.currentTime.date} ${this.currentTime.hour}:${this.currentTime.minutes}:00.0`;
            let present = new Date(temp);
            let limit = new Date(promise.promiseTime)
            return (limit.getTime() - present.getTime())/1000/60 ;
        },
        getPromises: function(status){
            let promises = [];
            this.promises.forEach(promise=>{
                if(promise.status===status){
                    promises.push(promise);
                }
            })
            return promises;
        },
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
            axios.post("http://192.168.22.109:9000/promise", {
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
                        axios.get("http://192.168.22.109:9000/promise", {params:{userId:this.user.id}})
                            .then(res=>{
                                this.promises = res.data;
                                res.data.forEach(promise=>{
                                    this.getMembers(promise);
                                    if(this.getDateTime(promise)<0){
                                        promise.status = "end";
                                    }else if(this.getDateTime(promise)<60*24){
                                        promise.status = "today";
                                    }else{
                                        promise.status = "upcoming";
                                    }
                                });
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
            return new Promise(function(resolve, reject){
                navigator.geolocation.getCurrentPosition(resolve,reject);
            }).then(position=>{
                console.log(position)
                this.position.latitude = position.coords.latitude;
                this.position.longitude = position.coords.longitude;
                this.updateLocation();
            })
        },
        getRoomId: function(){
            var tempElem = document.createElement("textarea");
            tempElem.value = "http://192.168.22.109:9000/" + this.selectedPromise.invitation;
            document.body.appendChild(tempElem);
            tempElem.select();
            document.execCommand("copy");
            document.body.removeChild(tempElem);
            alert("주소가 클립보드에 복사되었습니다 친구에게 보내 주세요 >찡긋<");
        },
        arrive: function() {
            if(this.computeDistance(this.position.latitude,this.position.longitude,this.selectedPromise)<=0.2){
                let temp = {
                    userId:this.user.userId,
                    promiseId:this.selectedPromise.promiseId
                }
                // 정시 도착
                if(this.getTime(this.selectedPromise)>0){
                    temp.arrival = 1;
                }else{
                    //지각
                    temp.arrival = 0;
                }
                axios.put("http://192.168.22.109:9000/user/arrival",temp)
                    .then(res=>{
                        alert('도착처리 되었습니다')
                    })
                    .catch(e=>{
                        alert('실패')
                    })
            }else{
                alert('도착 후 시도해주세요')
            }
        },
        updateLocation: function(){
            axios.put("http://192.168.22.109:9000/user/position",{
                userId:this.user.userId,
                //promiseId:this.selectedPromise.promiseId,
                latitude:this.position.latitude,
                longitude:this.position.longitude
            })
                .then(res=>{
                    //alert('수정되었습니다.')
                }).catch(e=>{
                    alert(e)
                })
        },
        updateUser: function(){
            axios.put("http://192.168.22.109:9000/user", {
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
            axios.get("http://192.168.22.109:9000/promise/members", {params:{promiseId:promise.id}})
                .then(res=>{
                    promise.members= [];
                    res.data.forEach(e=>{
                        promise.members.push(e);
                        if(e.userId === this.user.id){
                            if(e.arrival && e.arrival===1){
                                promise.arrival='1'
                            }else{
                                promise.arrival='0'
                            }
                        }
                    })
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
        renewTime: function(time){
            setInterval(function(){
                let temp = new Date();
                time.month = temp.getMonth()+1;
                time.date = temp.getDate();
                time.hour = temp.getHours();
                time.minutes = temp.getMinutes();
            }, 60000);
        },
        logout: function(){
            axios.get("http://192.168.22.109:9000/logout")
                .then(res=>{
                	if(res.data){
                		this.user=null;
                		alert('로그아웃 되었습니다');
                		window.location.href = 'http://192.168.22.109:9000/';            		
                	}
                })
                .catch(e=>{
                    alert('로그아웃 실패')
                })
        },
        // 반환 거리 단위 (km)
        computeDistance: function (memberLatitude,memberLongitude, promise) {
            console.log(memberLatitude,memberLongitude)
            if(memberLatitude && memberLongitude){
                var startLatRads = this.degreesToRadians(memberLatitude);
                var startLongRads = this.degreesToRadians(memberLongitude);
                var destLatRads = this.degreesToRadians(promise.placeY);
                var destLongRads = this.degreesToRadians(promise.placeX);
    
                var Radius = 6371; //지구의 반경(km)
                var distance = Math.acos(Math.sin(startLatRads) * Math.sin(destLatRads) + 
                                Math.cos(startLatRads) * Math.cos(destLatRads) *
                                Math.cos(startLongRads - destLongRads)) * Radius;
                return distance.toFixed(2);

            }else{
                return "-";
            }
        },

        degreesToRadians: function (degrees) {
            radians = (degrees * Math.PI)/180;
            return radians;
        }
    },
    created: async function() { // vue.js가 가지고 있는 기본 메소드, 앱이 처음 생성될때 실행 되는 부분
        this.user = query;
        this.user.userId = query.id;
        this.renewTime(this.currentTime);
        await axios.get("http://192.168.22.109:9000/promise", {params:{userId:this.user.userId}})
            .then(res=>{
                this.promises = res.data;
                res.data.forEach(promise=>{
                    this.getMembers(promise);
                    if(this.getDateTime(promise)<0){
                        promise.status = "end";
                    }else if(this.getDateTime(promise)<60*24){
                        promise.status = "today";
                    }else{
                        promise.status = "upcoming";
                    }
                });
            })
            .catch(e=>{
                console.log(e);
            })
        this.promises.sort(function(a,b){
            aTime = new Date(a.promiseTime).getTime();
            bTime = new Date(b.promiseTime).getTime();
            if(aTime > bTime){
                return -1;
            }else if(aTime < bTime){
                return 1;
            }else{
                return 1;
            }
        })
        this.getLocation();
    }
});