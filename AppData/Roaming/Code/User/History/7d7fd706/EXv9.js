async function loadJson(url) {
	try {
		let response = await fetch(url);
		if (response.status == 200) {
			await response.json();
		}
		
		throw new Error(response.status);
	} catch (err) {
		alert(err);
	}
}

loadJson('no-such-user.json') // (3)