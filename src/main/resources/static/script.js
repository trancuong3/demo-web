document.addEventListener('DOMContentLoaded', function () {
    const heartButtons = document.querySelectorAll('form[action="/toggle-favorite"]');
    heartButtons.forEach(button => {
        button.addEventListener('submit', function (event) {
            event.preventDefault(); // Ngăn chặn form submit

            const heartIcon = button.querySelector('i'); // Biểu tượng trái tim
            const favoriteCountBadge = button.closest('.book-card').querySelector('.badge'); // Lấy badge tương ứng

            // Thay đổi màu của trái tim
            if (heartIcon.classList.contains('text-muted')) {
                heartIcon.classList.remove('text-muted');
                heartIcon.classList.add('text-danger');
            } else {
                heartIcon.classList.remove('text-danger');
                heartIcon.classList.add('text-muted');
            }

            // Cập nhật số lượng trái tim
            let count = parseInt(favoriteCountBadge.innerText) || 0; // Đảm bảo giá trị mặc định là 0
            count = heartIcon.classList.contains('text-danger') ? count + 1 : count - 1;
            favoriteCountBadge.innerText = count;
        });
    });

    // Tìm kiếm gợi ý
    document.getElementById('searchInput').addEventListener('input', function() {
        const query = this.value.toLowerCase();
        const suggestions = document.getElementById('suggestions');
        suggestions.innerHTML = ''; // Xóa gợi ý trước đó
        suggestions.style.display = 'none'; // Ẩn danh sách gợi ý

        if (query.length > 0) {
            fetch('/search/suggestions?query=' + query) // Gọi API để lấy gợi ý
                .then(response => response.json())
                .then(data => {
                    if (data.length > 0) {
                        suggestions.style.display = 'block'; // Hiển thị danh sách gợi ý
                        data.forEach(book => {
                            const suggestionItem = document.createElement('a');
                            suggestionItem.className = 'list-group-item list-group-item-action';
                            suggestionItem.href = '/books/' + book.id; // Đường dẫn đến trang chi tiết sách
                            suggestionItem.textContent = book.title; // Tên sách
                            suggestions.appendChild(suggestionItem);
                        });
                    }
                })
                .catch(error => console.error('Error fetching suggestions:', error));
        }
    });
});
