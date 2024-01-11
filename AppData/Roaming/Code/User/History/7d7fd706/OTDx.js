class HttpError extends Error {
  constructor(response) {
    super(`${response.status} for ${response.url}`);
    this.name = 'HttpError';
    this.response = response;
  }
}

async function loadJson(url) {
	try {
		let response = await fetch(url);
		if (response.status == 200) {
			return response.json();
		}
		
		throw new HttpError(response);
	} catch (err) {
		alert(err);
	}
}

// Запрашивать логин, пока github не вернёт существующего пользователя.
async function demoGithubUser() {
  let name = prompt("Введите логин?", "iliakan");
	do {
		try {
			let user = await loadJson(`https://api.github.com/users/${name}`);
			alert(`Полное имя: ${user.name}.`);
			return user;
		} catch (err) {
				alert("Такого пользователя не существует, пожалуйста, повторите ввод.");
			throw err;
		}
	} while(err instanceof HttpError && err.response.status == 404)

}

demoGithubUser();