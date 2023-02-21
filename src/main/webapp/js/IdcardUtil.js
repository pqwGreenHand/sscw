function checkId(pId) {
    //检查身份证号码 
    var arrVerifyCode = [1, 0, "x", 9, 8, 7, 6, 5, 4, 3, 2];
    var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
    var Checker = [1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1];
    
    if (pId.length != 15 && pId.length != 18){
    	return "身份证号共有15位或18位";
    } 
    
    var Ai = pId.length == 18 ? pId.substring(0, 17) : pId.slice(0, 6) + "19" + pId.slice(6, 16);
    if (!/^\d+$/.test(Ai)){
    	return "身份证除最后一位外，必须为数字！";
    } 
    
    pId=pId.toLowerCase();
    
    var yyyy = Ai.slice(6, 10), mm = Ai.slice(10, 12) - 1, dd = Ai.slice(12, 14);
    var d = new Date(yyyy, mm, dd), now = new Date();
    var year = d.getFullYear(), mon = d.getMonth(), day = d.getDate();
    if (year != yyyy || mon != mm || day != dd || d > now || year < 1800){
    	return "身份证输入错误！";
    } 

    for (var i = 0, ret = 0; i < 17; i++) {
    	ret += Ai.charAt(i) * Wi[i];
    }
    
    Ai += arrVerifyCode[ret %= 11];
    return pId.length == 18 && pId != Ai ? "身份证输入错误！" : true;
};

//根据身份证取生日
function getBirth(ic) {
    if (isNaN(ic)) return;
    var ic = String(ic);
    //获取出生日期
    var birth = ic.substring(6, 10) + "-" + ic.substring(10, 12) + "-" + ic.substring(12, 14);
    return birth;
}

//根据身份证性别
function getSex(ic) {
    //ic = checkId(ic);
    if (isNaN(ic)) return;
    var ic = String(ic);
    //获取性别
    var gender = ic.slice(14, 17) % 2 ? "0" : "1"; // 0代表男性，1代表女性
    return gender;
}

//根据身份证取年龄
function getAge(ic) {
    //ic = checkId(ic);
    if (isNaN(ic)) return;
    var ic = String(ic);
    //获取年龄
    var myDate = new Date();
    var month = myDate.getMonth() + 1;
    var day = myDate.getDate();
    var age = myDate.getFullYear() - ic.substring(6, 10) - 1;
    if (ic.substring(10, 12) < month || ic.substring(10, 12) == month && ic.substring(12, 14) <= day) {
        age++;
    }
    return age;
}
