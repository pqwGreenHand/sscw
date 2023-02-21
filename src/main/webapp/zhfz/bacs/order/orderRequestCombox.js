var nationlist=null;
$(function() {
    $("#interpreter").combobox({
        valueField: 'id',
        textField: 'text',
        url: './interpret.json'
    });
    $("#editinterpreter").combobox({
    	valueField: 'id',
    	textField: 'text',
    	url: './interpret.json'
    });
    $('#interrogatearea').combobox({
        url: 'comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            $('#interrogatearea').combobox('setValue', data[0].id);
        },
        onChange: function (newValue, oldValue) {
        }
    });
    $('#certificateTypeId').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#certificateTypeId').combobox('setValue', data[0].id);
        }
    });
    codeCombo('person_country', 'GJID', 156);
    // codeCombo('person_nation', 'MZID', 1);

    codeCombo('country', 'GJID', 156);
    codeCombo('sex_person','XBID','');
    loadNation();
    // codeCombo('nation', 'MZID', 1);
});
function loadSex() {
    codeCombo('person_sex','XBID','');
}
function loadSexadd() {
	codeCombo('addperson_sex','XBID','');
}
function loadSexEdit() {
	codeCombo('edit_sex','XBID','');
}
//加载特殊身份
function loadSpecialIdentity(){
    $('#special_identity,#specialIdentity,#edit_specialIdentity,#addspecial_identity,#person_specialIdentity').combobox({
        data: [{
            label: '记者',
            value: '记者'
        },{
            label: '律师',
            value: '律师'
        },{
            label: '社会公众人物或网络人',
            value: '社会公众人物或网络人'
        },{
            label: '县以上人大代表',
            value: '县以上人大代表'
        },{
            label: '区县以上政协委员',
            value: '区县以上政协委员'
        },{
            label: '民主党派区县以上组织负责人',
            value: '民主党派区县以上组织负责人'
        },{
            label: '宗教界神职人员',
            value: '宗教界神职人员'
        },{
            label: '文化艺术、体育界知名人士',
            value: '文化艺术、体育界知名人士'
        },{
            label: '副局级以上干部',
            value: '副局级以上干部'
        },{
            label: '省军级以上干部的直系亲属',
            value: '省军级以上干部的直系亲属'
        }
        ,{
            label: '其他',
            value: '其他'
        }],
        valueField:'value',
        textField:'label'
    });
}
//加载人员类型
function loadPersonInfo(id, value)
{
    //加载人员类型
    var personInfo = value;
    $(id).combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        },{
            label: '老年人',
            value: '老年人'
        },{
            label: '特殊疾病',
            value: '特殊疾病'
        },{
            label: '孕妇',
            value: '孕妇'
        },{
            label: '残疾人',
            value: '残疾人'
        },{
            label: '成年人',
            value: '成年人'
        }],
        valueField:'value',
        textField:'label',
        onLoadSuccess: function(data){
            $(id).combobox('setValue', personInfo);
        },
        onChange:function(value)
        {
            var eisJuvenile=document.getElementsByName("eisJuvenile");//未成年人
            var eisGravida=document.getElementsByName("eisGravida");//孕妇

            if(value=='未成年人')
            {
                eisJuvenile[0].checked = true;
                eisGravida[0].checked = false;
            } else if (value=='孕妇')
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = true;
            }else
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = false;
            }
        }
    });
}


//加载人员类型
function loadPersonInfoEdit(id, value)
{
    //加载人员类型
    var personInfo = value;
    $(id).combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        },{
            label: '老年人',
            value: '老年人'
        },{
            label: '特殊疾病',
            value: '特殊疾病'
        },{
            label: '孕妇',
            value: '孕妇'
        },{
            label: '残疾人',
            value: '残疾人'
        },{
            label: '成年人',
            value: '成年人'
        }],
        valueField:'value',
        textField:'label',
        onLoadSuccess: function(data){
            $(id).combobox('setValue', personInfo);
        },
        onChange:function(value)
        {
            var eisJuvenile=document.getElementsByName("edit_eisJuvenile");//未成年人
            var eisGravida=document.getElementsByName("edit_eisGravida");//孕妇

            if(value=='未成年人')
            {
                eisJuvenile[0].checked = true;
                eisGravida[0].checked = false;
            } else if (value=='孕妇')
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = true;
            }else
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = false;
            }
        }
    });
}

function setWuMingShi(){
    var person_certificate_type=$('#person_certificate_type').combobox('getValue');
    if(person_certificate_type==7){
        $('#person_name').val('无名氏');
        $('#person_certificate_no').val('XXXXX.....XXXXX');
    }
}
function loadNation(nation){
    nation = nation?nation:1;
    jQuery.ajax({
        type: 'get',
        async: true,
        dataType: 'json',
        url:contextPath+"/zhfz/common/code/listCodeByType.do?type=MZID",
        success: function (data) {
            nationlist=data;
            if(data!=null){
                $('#person_nation').combobox({
                    valueField:'codeKey',
                    textField:'codeValue',
                    onLoadSuccess : function() {
                        $('#person_nation').combobox('setValue',nation);
                    }
                });
                $('#edit_nation').combobox({
                    valueField:'codeKey',
                    textField:'codeValue',
                    onLoadSuccess : function() {
                        $('#edit_nation').combobox('setValue',nation);
                    }
                });
                $('#addperson_nation').combobox({
                	valueField:'codeKey',
                	textField:'codeValue',
                	onLoadSuccess : function() {
                		$('#addperson_nation').combobox('setValue',nation);
                	}
                });
                $('#person_nation').combobox( 'loadData',data);
                $('#edit_nation').combobox( 'loadData',data);
                $('#addperson_nation').combobox( 'loadData',data);
            }
        },
        error:function(data){
            console.log(data);
        }
    });
}
//加载添加预约信息的办案场所下拉框
function loadOrderArea(areaId){
    $('#orderarea').combobox({
        url: 'comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            /*if(areaId == null || areaId == "" || areaId == 0){
                $('#orderarea').combobox('setText',"请选择办案场所");
            } else{
                $('#orderarea').combobox("setValue",areaId);
            }*/
            $('#orderarea').combobox("setValue",data[0].id);
        },
        onChange: function (newValue, oldValue) {
        }
    });
    $('#editorderarea').combobox({
    	url: 'comboArea.do',
    	valueField: 'id',
    	textField: 'name',
    	onLoadSuccess: function (data) {
    		if(areaId == null || areaId == "" || areaId == 0){
    			$('#editorderarea').combobox('setText',"请选择办案场所");
    		} else{
    			$('#editorderarea').combobox("setValue",areaId);
    		}
    	},
    	onChange: function (newValue, oldValue) {
    	}
    });
}
