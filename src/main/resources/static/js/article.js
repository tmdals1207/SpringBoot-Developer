// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        }

        function fail() {
            alert('삭제 실패했습니다.');
            location.replace('/articles');
        }

        httpRequest('DELETE',`/api/articles/${id}`, null, success, fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        })

        function success() {
            alert('수정 완료되었습니다.');
            location.replace(`/articles/${id}`);
        }

        function fail() {
            alert('수정 실패했습니다.');
            location.replace(`/articles/${id}`);
        }

        httpRequest('PUT',`/api/articles/${id}`, body, success, fail);
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/articles');
        };
        function fail() {
            alert('등록 실패했습니다.');
            location.replace('/articles');
        };

        httpRequest('POST','/api/articles', body, success, fail)
    });
}


// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }

        const refresh_token = getCookie('refresh_token');

        if (response.status === 401 && refresh_token) {
            // 액세스 토큰 재발급 시도
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: refresh_token,
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    } else {
                        // 재발급 실패: 토큰 삭제 후 로그인 페이지 이동
                        handleTokenError();
                    }
                })
                .then(result => {
                    if (!result) return; // 위에서 실패 처리함

                    // 재발급 성공
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => {
                    handleTokenError();
                });
        } else {
            // 401이지만 리프레시 토큰도 없으면 바로 로그인 페이지로
            if (response.status === 401) {
                handleTokenError();
            } else {
                return fail();
            }
        }
    }).catch(error => {
        console.error(error);
        fail();
    });
}

// 토큰 에러 처리: 토큰 삭제 후 로그인 페이지 이동
function handleTokenError() {
    localStorage.removeItem('access_token'); // 로컬 스토리지 토큰 삭제
    document.cookie = 'refresh_token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;'; // 리프레시 토큰 쿠키 삭제 (유닉스 시간 0초)
    alert('세션이 만료되었습니다. 다시 로그인 해주세요.');
    window.location.href = '/login'; // 로그인 페이지로 이동 (경로는 본인 사이트에 맞게 수정!)
}
