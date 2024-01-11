class HttpError extends Error {
  constructor(response) {
    super(`${response.status} for ${response.url}`);
    this.name = 'HttpError';
    this.response = response;
  }
}

async function loadJson(url) {
	let response = await fetch(url);
	if (response.status == 200) {
		return response.json();
	}
	
	throw new HttpError(response);
}

// Запрашивать логин, пока github не вернёт существующего пользователя.
async function demoGithubUser() {
	do {
		let name = prompt("Введите логин?", "iliakan");
		let user = await loadJson(`https://api.github.com/users/${name}`);
		if (user instanceof HttpError && user.response.status == 404) {
			alert("Такого пользователя не существует, пожалуйста, повторите ввод.");
		} else {
			throw err;
		}
		alert(`Полное имя: ${user.name}.`);
		return user;
	} while(true)

}

demoGithubUser();