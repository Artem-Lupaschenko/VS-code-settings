// // Отримуємо посилання на кнопку "Подробнее"
// let newItem = document.querySelector('.new-item');
 
// let newItems = document.querySelector('.new-items');

// // Отримуємо посилання на елемент, який потрібно випливаючий блок
// let popup = document.createElement('div');
// popup.className = 'details';
// popup.innerHTML = '<p>Детали товара загруженные с базы данных</p><button class="btn close-btn"><</button>';
// // Додаємо випливаючий блок до тіла документа
// newItem.prepend(popup);

// // Додаємо обробник події для кнопки "Подробнее"
// newItems.addEventListener('click', function(event) {
// 	let target = event.target;

// 	if (!target.classList.contains("detail-btn") && !target.classList.contains("close-btn")) return;
// 	// Забороняємо стандартну дію посилання
// 	event.preventDefault();

// 	// Показуємо випливаючий блок
// 	popup.classList.toggle("show")
// 	// Змінюємо видимість кнопки закриття
// 	let closeButton = popup.querySelector('.close-btn');
// 	closeButton.style.display = closeButton.style.display === 'block' ? 'none' : 'block';
// });

let popup = document.createElement('div');
popup.className = 'details';

let newItems = document.querySelector('.new-items');

newItems.addEventListener('click', function(event) {
	let target = event.target;
	if (!target.classList.contains("detail-btn")) return;
	event.preventDefault();
	// Отримуємо посилання на елемент, який потрібно випливаючий блок
	
	popup.innerHTML = '<p>Детали товара загруженные с базы данных</p><button class="btn close-btn"><</button>';
	// Додаємо випливаючий блок до тіла документа
	target.parentElement.prepend(popup);

	popup.classList.toggle("show")
	let closeButton = popup.querySelector('.close-btn');
	closeButton.style.display = closeButton.style.display === 'block' ? 'none' : 'block';

});

// Додаємо обробник події для сховання випливаючого блоку при кліку на нього
// popup.addEventListener('click', function() {
//     // Ховаємо випливаючий блок
//     popup.style.display = 'none';
// });