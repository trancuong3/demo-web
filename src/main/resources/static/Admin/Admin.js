$(document).ready(function () {
    $('#searchInput').on('input', function () {
        var query = $(this).val();

        if (query.length > 2) {
            $.ajax({
                url: '/search/suggestions',
                type: 'GET',
                data: {query: query},
                success: function (data) {
                    $('#suggestions').empty().show();

                    if (data.length > 0) {
                        $.each(data, function (index, book) {
                            $('#suggestions').append('<a href="#" class="list-group-item list-group-item-action">' + book.title + '</a>');
                        });
                    } else {
                        $('#suggestions').append('<a href="#" class="list-group-item list-group-item-action">No results found</a>');
                    }
                }
            });
        } else {
            $('#suggestions').hide();
        }
    });
    $(document).click(function (e) {
        if (!$(e.target).closest('#searchInput').length) {
            $('#suggestions').hide();
        }
    });
});

$('#imageUrl').on('input', function () {
    var imageUrl = $(this).val();
    $('#previewImage').attr('src', imageUrl).show();
});

$(document).ready(function () {
    $("#searchInput").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#productList tr").filter(function () {
            $(this).toggle($(this).data("title").indexOf(value) > -1);
        });
    });
});
