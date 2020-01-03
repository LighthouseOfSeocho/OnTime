module.exports = {
    "env": {
        "browser": true,
        "es6": true
    },
    "extends": "eslint:recommended",
    "globals": {
        "Atomics": "readonly",
        "SharedArrayBuffer": "readonly"
    },
    "parserOptions": {
        "ecmaVersion": 2018,
        "sourceType": "module"
    },
     "rules": {
        "no-undef": 2,                          //정의되지 않은 변수         
        "no-unused-vars": 1,                    //사용하지 않은 변수
        "quotes": 1,                            //double quotes
        "comma-spacing" : 1,                    //쉼표 뒤에 공백
        "curly" : 2,                            //여러줄에 if문은 중괄호
        "newline-before-return" : 1,            //return전에는 한 줄 띄기
        "no-multi-spaces": 1,                   //띄어쓰기 많이 금지(1번만)
        "no-multiple-empty-lines" : 2,                              //빈 라인 여러개 금지
        "no-mixed-spaces-and-tabs": 1,          //tab,띄어쓰기 혼용 금지
        "keyword-spacing" : [ "warn", 
        { "overrides" : 
        {"if" : { "after" : false}, 
        "for": { "after": false },
        "while": { "after": false } }}],        //if,for,while 소괄호 공백 스타일
        "space-before-function-paren": ["warn", "never"],      //함수 선언 앞에 공백 추가 안함
        "space-infix-ops" : 1,                  //연산자 쓸 때 공백
        "comma-spacing" : 1,                    //쉼표 뒤에 공백
        "brace-style" : 1,                      //else문은 중괄호와 같이
        //"one-var" : 1,                          //var 하나에 한 개의 값
        "camelcase" : 2,                        //function myFunction () { }
        "comma-style" : 1,                      //쉼표를 사용할 경우 현재 행 끝
        "func-call-spacing" : 1,                //함수식별자와 호출사이에는 공백이 없어야 합니다. 
                                                 /*console.log ('hello') // ✗ 피하세요
                                                 console.log('hello')  // ✓ 좋아요 */
        "new-cap" : 2,                          //생성자 이름은 대문자로 시작해야합니다.
        "new-parens" : 2,                       //인수가 없는 생성자는 괄호로 호출해야합니다
        "accessor-pairs" : 1,                   //setter가 정의되면 객체에 getter가 포함되어 있어야 합니다.
        "no-class-assign" : 1,                  //클래스로 선언된 변수를 수정하지 마세요
        "semi" : ["error", "always"],            //semi colon 필수,  함수 내부에 함수는 ; 찍으셈
        "indent" : ["error", 4],
    }
};