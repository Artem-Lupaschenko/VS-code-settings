// Отримуємо посилання на кнопку "Подробнее"
let newItem = document.querySelectorAll('.new-item');
 
let newItems = document.querySelector('.new-items');

// Отримуємо посилання на елемент, який потрібно випливаючий блок
let popup = document.createElement('div');
popup.className = 'details';
for (item of newItem) {
	// Додаємо випливаючий блок до тіла документа
	item.prepend(popup);
	popup.innerHTML = '<p>Детали товара загруженные с базы данных</p><button class="btn close-btn"><</button>';
}

// Додаємо обробник події для кнопки "Подробнее"
newItems.addEventListener('click', function(event) {
	let target = event.target;
	if (!target.classList.contains("detail-btn") && !target.classList.contains("close-btn")) return;
	// Забороняємо стандартну дію посилання
	event.preventDefault();

	// Показуємо випливаючий блок
	popup.classList.toggle("show")
	// Змінюємо видимість кнопки закриття
	let closeButton = popup.querySelector('.close-btn');
	closeButton.style.display = closeButton.style.display === 'block' ? 'none' : 'block';
});

// Додаємо обробник події для сховання випливаючого блоку при кліку на нього
// popup.addEventListener('click', function() {
//     // Ховаємо випливаючий блок
//     popup.style.display = 'none';
// });