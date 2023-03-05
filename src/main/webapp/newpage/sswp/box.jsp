<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%@ include file="../taglibs.jsp" %>
<html>
<head>
    <%
        String ssareaid = request.getParameter("ssareaid");
    %>
    <script type="text/javascript" src="box.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%--<%@ include file="./../../zhfz/common/common-head.jsp" %>--%>
    <%@ include file="../common.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <%--<script type="text/javascript" src="layer.js"></script>--%>
    <script>
        $(function () {
            mHead();
        });

        function mHead() {
            $(".m-head .user").hover(function () {
                $(this).addClass("open");
            }, function () {
                $(this).removeClass("open");
            })
            $(".m-head .user").on("mousemove", "*", function () {
                $(this).parents(".user").addClass("open");
            })
        }
    </script>
    <title>随身物品</title>
</head>
<body style="width: 100%; height: 100%;" onload="loadinfo();">
<input id="ssareaid" type="hidden" name="ssareaid" value="<%=ssareaid%>"></input>
<div class="page locker-box">
    <div class="main" style="width: 100%; height: 100%; overflow-y: auto;">
        <div class="control-bar">
            <div style=" overflow: hidden;width: 700px;left: 50%;margin-left: -281px;position: absolute;">
                <div style="padding-left: 100px">
                   <%-- <a href="javascript:void(0)" onclick="goBelongin('<%=ssareaid%>');" data-options="iconCls:'icon-undo'"
                       class="easyui-linkbutton button-line-blue"
                       style="width: 80px;margin-left: 10px;">存物</a>
                    <a href="javascript:void(0)" onclick="goBelongout('<%=ssareaid%>');" data-options="iconCls:'icon-redo'"
                       class="easyui-linkbutton button-line-blue"
                       style="width: 80px;margin-left: 10px;">取物</a>--%>
                    <a href="javascript:void(0)" onclick="goBelongin('<%=ssareaid%>');"  data-options="iconCls:'icon-undo'"  class="easyui-linkbutton button-line-blue"
                       style="width: 100px;height:40px;margin-left: 10px;">存物</a>
                    <a href="javascript:void(0)" onclick="goBelongout('<%=ssareaid%>');"  data-options="iconCls:'icon-redo'"  class="easyui-linkbutton button-line-blue"
                       style="width: 100px;height:40px;margin-left: 10px;">取物</a>
                  <%--  <a href="javascript:void(0)" onclick="goBelongManage('<%=ssareaid%>');" data-options="iconCls:'icon-set'"
                       class="easyui-linkbutton button-line-blue"
                       style="width: 80px;margin-left: 10px;">管理</a>--%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0)" onclick="javascript:window.location.href ='index.jsp';" data-options="iconCls:'icon-back'"
                       class="easyui-linkbutton button-line-blue"
                       style="width: 80px;margin-left: 10px;">返回</a>
                    <%--<button class="m-btn-2" style="background: #08926a;" id="belongin" onclick="goBelongin('<%=ssareaid%>');">
                        <span><i style="background-image: url(image/locker-8.png);"></i>存物</span>
                    </button>
                    <button class="m-btn-2" style="background: #ad6d31;" id="belongout" onclick="goBelongout('<%=ssareaid%>');">
                        <span><i style="background-image: url(image/locker-9.png);"></i>取物</span>
                    </button>
                    <button class="m-btn-2" style="background: #0f5896;" id="belongset" onclick="goBelongManage('<%=ssareaid%>');">
                        <span><i style="background-image: url(image/locker-10.png);" ></i>管理</span>
                    </button>--%>

                </div>
                <div style="padding-left: 150px;padding-top: 10px;">
                    <div class="tip i1">
                        <i></i>
                        <span>可储存</span>
                    </div>
                    <div class="tip i2">
                        <i></i>
                        <span>已储存</span>
                    </div>
                    <div class="tip i3">
                        <i></i>
                        <span>不可用</span>
                    </div>
                </div>


                <%--
                    <div class="tip i1">
                        <i></i>
                        <span>可储存</span>
                    </div>
                    <div class="tip i2">
                        <i></i>
                        <span>已储存</span>
                    </div>
                    <div class="tip i3">
                        <i></i>
                        <span>不可用</span>
                    </div>
                    <button class="m-btn-2" style="background: #0f5896;" id="belongxt" onclick="javascript:window.location.href ='index.jsp';">
                        <span><i style="background-image: url(image/locker-8.png);" ></i>返回</span>
                    </button>--%>
            </div>
        </div>
        <div class="box-all">
            <div class="box-all-in">
                <div class="box" id="box">
                </div>
                <div class="box" id="box_area1" style="width: 1150px;display: none ">
                    <div class="box-title">
                        <span>随身物品智能储物柜</span>
                    </div>
                    <div class="list">
                        <div class="row">
                            <div class="item blank" id="box217" onclick="showin('217','07','01')">
                                <div class="item-in" id="item217">
                                    <i>01</i>
                                </div>
                            </div>
                            <div class="item blank" id="box218" onclick="showin('218','08','02')">
                                <div class="item-in" id="item218">
                                    <i>02</i>
                                </div>
                            </div>
                            <div class="item blank" id="box219" onclick="showin('219','09','03')">
                                <div class="item-in" id="item219">
                                    <i>03</i>
                                </div>
                            </div>
                            <div class="item blank" id="box220" onclick="showin('220','10','04')">
                                <div class="item-in" id="item220">
                                    <i>04</i>
                                </div>
                            </div>
                            <%--<div class="item blank" id="box346" onclick="showin('346','01')">
                                <div class="item-in" id="item346">
                                    <i>01</i>
                                </div>
                            </div>
                             <div class="item blank" id="box347" onclick="showin('347','02')">
                                <div class="item-in" id="item347">
                                    <i>02</i>
                                </div>
                            </div>--%>
                            <div class="item blank" id="box211" onclick="showin('211','01','05')">
                                <div class="item-in" id="item211">
                                    <i>05</i>
                                </div>
                            </div>
                            <div class="item blank" id="box212" onclick="showin('212','02','06')">
                                <div class="item-in" id="item212">
                                    <i>06</i>
                                </div>
                            </div>
                            <div class="item blank" id="box213" onclick="showin('213','03','07')">
                                <div class="item-in" id="item213">
                                    <i>07</i>
                                </div>
                            </div>
                            <div class="item blank" id="box214" onclick="showin('214','04','08')">
                                <div class="item-in" id="item214">
                                    <i>08</i>
                                </div>
                            </div>
                            <div class="item blank" id="box215" onclick="showin('215','05','09')">
                                <div class="item-in" id="item215">
                                    <i>09</i>
                                </div>
                            </div>
                            <div class="item blank" id="box216" onclick="showin('216','06','10')">
                                <div class="item-in" id="item216">
                                    <i>10</i>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="item blank" id="box227" onclick="showin('227','17','11')">
                                <div class="item-in" id="item227">
                                    <i>11</i>
                                </div>
                            </div>
                            <div class="item blank" id="box228" onclick="showin('228','18','12')">
                                <div class="item-in" id="item228">
                                    <i>12</i>

                                </div>
                            </div>
                            <div class="item blank" id="box229" onclick="showin('229','19','13')">
                                <div class="item-in" id="item229">
                                    <i>13</i>

                                </div>
                            </div>
                            <div class="item blank" id="box230" onclick="showin('230','20','14')">
                                <div class="item-in" id="item230">
                                    <i>14</i>

                                </div>
                            </div>
                            <%--<div class="item blank" id="box215" onclick="showin('215','03')">
                                <div class="item-in" id="item215">
                                    <i>03</i>

                                </div>
                            </div>
                            <div class="item blank" id="box216" onclick="showin('216','04')">
                                <div class="item-in" id="item216">
                                    <i>04</i>

                                </div>
                            </div>--%>
                            <div class="item blank" id="box221" onclick="showin('221','11','15')">
                                <div class="item-in" id="item221">
                                    <i>15</i>

                                </div>
                            </div>
                            <div class="item blank" id="box222" onclick="showin('222','12','16')">
                                <div class="item-in" id="item222">
                                    <i>16</i>

                                </div>
                            </div>
                            <div class="item blank" id="box223" onclick="showin('223','13','17')">
                                <div class="item-in" id="item223">
                                    <i>17</i>

                                </div>
                            </div>
                            <div class="item blank" id="box224" onclick="showin('224','14','18')">
                                <div class="item-in" id="item224">
                                    <i>18</i>

                                </div>
                            </div>
                            <div class="item blank" id="box225" onclick="showin('225','15','19')">
                                <div class="item-in" id="item225">
                                    <i>19</i>

                                </div>
                            </div>
                            <div class="item blank" id="box226" onclick="showin('226','16','20')"> >
                                <div class="item-in" id="item226">
                                    <i>20</i>

                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="item blank" id="box237" onclick="showin('237','27','21')">
                                <div class="item-in" id="item237">
                                    <i>21</i>
                                </div>
                            </div>
                            <div class="item blank" id="box238" onclick="showin('238','28','22')">
                                <div class="item-in" id="item238">
                                    <i>22</i>

                                </div>
                            </div>
                            <div class="item blank" id="box239" onclick="showin('239','29','23')">
                                <div class="item-in" id="item239">
                                    <i>23</i>

                                </div>
                            </div>
                            <div class="item blank" id="box240" onclick="showin('240','30','24')">
                                <div class="item-in" id="item240">
                                    <i>24</i>

                                </div>
                            </div>
                            <%-- <div class="item blank" id="box221" onclick="showin('221','05')">
                                 <div class="item-in" id="item221">
                                     <i>05</i>

                                 </div>
                             </div>
                             <div class="item blank" id="box222" onclick="showin('222','06')">
                                 <div class="item-in" id="item222">
                                     <i>06</i>

                                 </div>
                             </div>--%>
                            <div class="item blank" id="box231" onclick="showin('231','21','25')">
                                <div class="item-in" id="item231">
                                    <i>25</i>

                                </div>
                            </div>
                            <div class="item blank" id="box232" onclick="showin('232','22','26')">
                                <div class="item-in" id="item232">
                                    <i>26</i>

                                </div>
                            </div>
                            <div class="item blank" id="box233" onclick="showin('233','23','27')">
                                <div class="item-in" id="item233">
                                    <i>27</i>

                                </div>
                            </div>
                            <div class="item blank" id="box234" onclick="showin('234','24','28')">
                                <div class="item-in" id="item234">
                                    <i>28</i>

                                </div>
                            </div>
                            <div class="item blank" id="box235" onclick="showin('235','25','29')">
                                <div class="item-in" id="item235">
                                    <i>29</i>

                                </div>
                            </div>
                            <div class="item blank" id="box236" onclick="showin('236','26','30')">
                                <div class="item-in" id="item236">
                                    <i>30</i>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="item blank" id="box247" onclick="showin('247','37','31')">
                                <div class="item-in" id="item247">
                                    <i>31</i>
                                </div>
                            </div>
                            <div class="item blank" id="box248" onclick="showin('248','38','32')">
                                <div class="item-in" id="item248">
                                    <i>32</i>

                                </div>
                            </div>
                            <div class="item blank" id="box249" onclick="showin('249','39','33')">
                                <div class="item-in" id="item249">
                                    <i>33</i>

                                </div>
                            </div>
                            <div class="item blank" id="box250" onclick="showin('250','40','34')">
                                <div class="item-in" id="item250">
                                    <i>34</i>

                                </div>
                            </div>
                            <%--<div class="item blank" id="box232" onclick="showin('232','07')">
                                <div class="item-in" id="item232">
                                    <i>07</i>

                                </div>
                            </div>
                            <div class="item blank" id="box233" onclick="showin('233','08')">
                                <div class="item-in" id="item233">
                                    <i>08</i>

                                </div>
                            </div>--%>
                            <div class="item blank" id="box241" onclick="showin('241','31','35')">
                                <div class="item-in" id="item241">
                                    <i>35</i>

                                </div>
                            </div>
                            <div class="item blank" id="box242" onclick="showin('242','32','36')">
                                <div class="item-in" id="item242">
                                    <i>36</i>

                                </div>
                            </div>
                            <div class="item blank" id="box243" onclick="showin('243','33','37')">
                                <div class="item-in" id="item243">
                                    <i>37</i>

                                </div>
                            </div>
                            <div class="item blank" id="box244" onclick="showin('244','34','38')">
                                <div class="item-in" id="item244">
                                    <i>38</i>

                                </div>
                            </div>
                            <div class="item blank" id="box245" onclick="showin('245','35','39')">
                                <div class="item-in" id="item245">
                                    <i>39</i>

                                </div>
                            </div>
                            <div class="item blank" id="box246" onclick="showin('246','36','40')">
                                <div class="item-in" id="item246">
                                    <i>40</i>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="item blank" id="box257" onclick="showin('257','47','41')">
                                <div class="item-in" id="item257">
                                    <i>41</i>
                                </div>
                            </div>
                            <div class="item blank" id="box258" onclick="showin('258','48','42')">
                                <div class="item-in" id="item258">
                                    <i>42</i>

                                </div>
                            </div>
                            <div class="item blank" id="box259" onclick="showin('259','49','43')">
                                <div class="item-in" id="item259">
                                    <i>43</i>

                                </div>
                            </div>
                            <div class="item blank" id="box260" onclick="showin('260','50','44')">
                                <div class="item-in" id="item260">
                                    <i>44</i>

                                </div>
                            </div>
                            <%-- <div class="item blank" id="box243" onclick="showin('243','9')">
                                 <div class="item-in" id="item243">
                                     <i>9</i>

                                 </div>
                             </div>
                             <div class="item blank" id="box244" onclick="showin('244','10')">
                                 <div class="item-in" id="item244">
                                     <i>10</i>

                                 </div>
                             </div>--%>
                            <div class="item blank" id="box251" onclick="showin('251','41','45')">
                                <div class="item-in" id="item251">
                                    <i>45</i>

                                </div>
                            </div>
                            <div class="item blank" id="box252" onclick="showin('252','42','46')">
                                <div class="item-in" id="item252">
                                    <i>46</i>

                                </div>
                            </div>
                            <div class="item blank" id="box253" onclick="showin('253','43','47')">
                                <div class="item-in" id="item253">
                                    <i>47</i>

                                </div>
                            </div>
                            <div class="item blank" id="box254" onclick="showin('254','44','48')">
                                <div class="item-in" id="item254">
                                    <i>48</i>

                                </div>
                            </div>
                            <div class="item blank" id="box255" onclick="showin('255','45','49')">
                                <div class="item-in" id="item255">
                                    <i>49</i>

                                </div>
                            </div>
                            <div class="item blank" id="box256" onclick="showin('256','46','50')">
                                <div class="item-in">
                                    <i>50</i>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="item blank da left" id="box267" onclick="showin('267','57','51')">
                                <div class="item-in" id="item267">
                                    <i>51</i>
                                </div>
                            </div>
                            <div class="item blank da left" id="box268" onclick="showin('268','58','52')">
                                <div class="item-in" id="item268">
                                    <i>52</i>

                                </div>
                            </div>
                            <div class="item blank da left" id="box269" onclick="showin('269','59','53')">
                                <div class="item-in" id="item269">
                                    <i>53</i>

                                </div>
                            </div>
                            <div class="item blank da left" id="box270" onclick="showin('270','60','54')">
                                <div class="item-in" id="item270">
                                    <i>54</i>

                                </div>
                            </div>
                            <%-- <div class="item blank" id="box2431" onclick="showin('243','11')">
                                 <div class="item-in" id="item2431">
                                     <i>11</i>

                                 </div>
                             </div>
                             <div class="item blank" id="box2441" onclick="showin('244','12')">
                                 <div class="item-in" id="item2441">
                                     <i>12</i>

                                 </div>
                             </div>--%>
                            <div class="item blank da left" id="box261" onclick="showin('261','51','55')">
                                <div class="item-in" id="item261">
                                    <i>55</i>

                                </div>
                            </div>
                            <div class="item blank da left" id="box262" onclick="showin('262','52','56')">
                                <div class="item-in" id="item262">
                                    <i>56</i>

                                </div>
                            </div>
                            <div class="item blank da left" id="box263" onclick="showin('263','53','57')">
                                <div class="item-in" id="item263">
                                    <i>57</i>

                                </div>
                            </div>
                            <div class="item blank da left" id="box264" onclick="showin('264','54','58')">
                                <div class="item-in" id="item264">
                                    <i>58</i>

                                </div>
                            </div>
                            <div class="item blank da left" id="box265" onclick="showin('265','55','59')">
                                <div class="item-in" id="item265">
                                    <i>59</i>

                                </div>
                            </div>
                            <div class="item blank da left" id="box266" onclick="showin('266','56','60')">
                                <div class="item-in" id="item266">
                                    <i>60</i>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="sky-bg" id="particles-js"></div>
    <img class="page-bg" style="background-color: #c4dbe8">
</div>
<script>
    var ssareaid = $("#ssareaid").val();
</script>
</body>
</html>