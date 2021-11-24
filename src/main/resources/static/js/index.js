let subscribeUrl = "http://localhost:8080/sub";

$(document).ready(function() {

    getMemos();

    if (sessionStorage.getItem("mytoken") != null) {
        let token = sessionStorage.getItem("mytoken");
        let eventSource = new EventSource(subscribeUrl + "?token=" + token);

        eventSource.addEventListener("addComment", function(event) {
            let message = event.data;
            alert(message);
        })

        eventSource.addEventListener("error", function(event) {
            eventSource.close()
        })
    }
})

function postMemo() {
    let title = $('#title').val();
    let content = $('#content').val();
    $('#title').val('')
    $('#content').val('')

    let data = {title: title, content: content};

    $.ajax({
        type: "POST",
        url: "/memo",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(data),
        success: function (res) {
            getMemos()
        }
    })

}

function getMemos() {
    $('#memos').empty()
    $.ajax({
        type: "GET",
        url: "/memos",
        success: function(res) {
            for (let i=0;i<res.length;i++) {
                let html = `<tr>
                            <th scope="row">${i+1}</th>
                            <td><a onclick="getMemo('${res[i]['id']}')">${res[i]['title']}</a></td>
                            <td>${res[i]['content']}</td>
                            </tr>`
                $('#memos').append(html);
            }
        }
    })
}

function getMemo(id) {

    $.ajax({
        type: "GET",
        url: `/memo?id=${id}`,
        success: function(res) {
            console.log(res);
            $('#input-title').val(res)


            $('#input-title').val(res['title']);
            $('#input-content').val(res['content'])

            $('#comment-box').empty();
            $('#comment-box').append(`<input type="text" placeholder="댓글을 입력하세요" id="comment">
                <button onclick="addComment('${res['id']}')">댓글입력</button>`)

            $('#comment-list').empty();
            let comments = res['comments'];
            for (let i=0;i<comments.length;i++) {
                let html = `<li class="list-group-item">${comments[i]['content']}</li>`
                $('#comment-list').append(html)
            }
            $('#detailModal').modal('show')
        }
    })
}

function addComment(id) {
    console.log("addComment = " + id);
    let content = $('#comment').val();

    $.ajax({
        type: "POST",
        url: `/memo/${id}/comment`,
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({content: content}),
        success: function (res) {
            getMemo(id);
        }
    })

}



function logout() {
    sessionStorage.clear();
    $.ajax({
        type: "POST",
        url: "/user/logout",
        success: function(res) {
            alert('로그아웃 성공')
            window.location.reload()
        }
    })
}

function signup() {
    let username = $('#username').val()
    let password = $('#password').val()
    let data = {username: username, password: password};

    $.ajax({
        type: "POST",
        url: "/user/signup",
        contentType: "application/json;charset=utf8",
        data: JSON.stringify(data),
        success: function(res) {
            alert('회원가입 성공')
            window.location.reload();
        }
    })
}

function signin() {
    let username = $('#username').val()
    let password = $('#password').val()
    let data = {username: username, password: password};

    $.ajax({
        type: "POST",
        url: "/user/signin",
        contentType: "application/json;charset=utf8",
        data: JSON.stringify(data),
        success: function(res) {
            alert('로그인 성공')
            let token = res['token']
            sessionStorage.setItem("mytoken", token);
            window.location.href='/';
        }
    })
}

$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    if (sessionStorage.getItem('mytoken') != null) {
        jqXHR.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem('mytoken'));
    }
});
