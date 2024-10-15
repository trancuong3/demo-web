document.addEventListener('DOMContentLoaded', function () {
    // Lấy tất cả các nút thích
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
});
