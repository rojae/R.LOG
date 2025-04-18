<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:10 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<jsp:include page="blogommon/top-meta.jsp"/>
<jsp:include page="blogommon/buttom-meta.jsp"/>
<style>
    body {
        background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAYAAACpSkzOAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAxMC8yOS8xMiKqq3kAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzVxteM2AAABHklEQVRIib2Vyw6EIAxFW5idr///Qx9sfG3pLEyJ3tAwi5EmBqRo7vHawiEEERHS6x7MTMxMVv6+z3tPMUYSkfTM/R0fEaG2bbMv+Gc4nZzn+dN4HAcREa3r+hi3bcuu68jLskhVIlW073tWaYlQ9+F9IpqmSfq+fwskhdO/AwmUTJXrOuaRQNeRkOd5lq7rXmS5InmERKoER/QMvUAPlZDHcZRhGN4CSeGY+aHMqgcks5RrHv/eeh455x5KrMq2yHQdibDO6ncG/KZWL7M8xDyS1/MIO0NJqdULLS81X6/X6aR0nqBSJcPeZnlZrzN477NKURn2Nus8sjzmEII0TfMiyxUuxphVWjpJkbx0btUnshRihVv70Bv8ItXq6Asoi/ZiCbU6YgAAAABJRU5ErkJggg==);
    }

    .error-template {
        padding: 40px 15px;
        text-align: center;
    }

    .error-actions {
        margin-top: 15px;
        margin-bottom: 15px;
    }

    .error-actions .btn {
        margin-right: 10px;
    }
</style>
<script>
    function doReport(){
        let data = {
            content: $('textarea[name="content"]').val()
        };

        $.ajax({
            url: '/error/report',
            method: "post",
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                showModal('알림창', data.response);

                setTimeout(function () {
                    window.location.replace('/');
                }, 2000);

            }
        });

    }

</script>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>
                    죄송합니다</h1>
                <h2>
                    요청하신 페이지는, 잘못된 접근입니다.
                </h2>
                <div class="error-details">
                    문의사항 : rojae@rlog.or.kr
                </div>
                <div class="error-actions">
                    <a href="/" class="btn btn-primary btn-lg">
                        <span class="glyphicon glyphicon-home"></span>
                        HOME </a>
                    <a class="btn btn-outline-danger btn-lg" data-toggle="collapse" href="#report" role="button" aria-expanded="false" aria-controls="report">
                        <span class="glyphicon glyphicon-envelope"></span>REPORT
                    </a>
                </div>
            </div>
            <div class="collapse" id="report">
                <label for="report-form">Bug Report Form</label>
                <textarea class="form-control" id="report-form" name="content" rows="5"></textarea>
                <button class="btn btn-primary btn-sm mt-1" onclick="doReport();">
                    <span class="glyphicon glyphicon-home"></span>report
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="blogommon/modal.jsp"/>
