// Отримуємо посилання на кнопку "Подробнее"
let newItem = document.querySelector('.new-item');
 
let newItems = document.querySelector('.new-items');




// Додаємо обробник події для кнопки "Подробнее"
newItems.addEventListener('click', function(event) {
	let target = event.target;
	if (!target.classList.contains("detail-btn") && !target.classList.contains("close-btn")) return;
	
	if (event.currentTarget.classList.contains("new-item")) {
		console.log(target)
		// Додаємо випливаючий блок до тіла документа
		event.currentTarget.prepend(popup);
	}
	// Забороняємо стандартну дію посилання
	event.preventDefault();

	// Показуємо випливаючий блок
	popup.classList.toggle("show")
	// Змінюємо видимість кнопки закриття
	let closeButton = popup.querySelector('.close-btn');
	closeButton.style.display = closeButton.style.display === 'block' ? 'none' : 'block';
});

// Додаємо обробник події для сховання випливаючого блоку при кліку на нього
newItem.addEventListener('click', function() {
	// Отримуємо посилання на елемент, який потрібно випливаючий блок
	let popup = document.createElement('div');
	popup.className = 'details';
	popup.innerHTML = '<p>Детали товара загруженные с базы данных</p><button class="btn close-btn"><</button>';
	
});