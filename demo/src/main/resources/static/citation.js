const searchInput = document.getElementById('searchInput');
        const suggestionsBox = document.getElementById('suggestions');

        searchInput.addEventListener('input', function() {
            const query = this.value;
            suggestionsBox.innerHTML = '';
            suggestionsBox.classList.add('hidden');

            if (query.length > 0) {
                fetch(`/autocompleteCitation?query=${query}`)
                    .then(response => response.json())
                    .then(data => {
                        data.forEach(suggestion => {
                            const suggestionItem = document.createElement('a');
                            suggestionItem.classList.add('list-group-item', 'list-group-item-action', 'hover:bg-blue-200', 'px-4', 'py-2', 'block');
                            suggestionItem.textContent = suggestion;
                            suggestionsBox.appendChild(suggestionItem);

                            suggestionItem.addEventListener('click', function() {
                                searchInput.value = suggestion;
                                suggestionsBox.innerHTML = '';
                                suggestionsBox.classList.add('hidden');
                            });
                        });
                        suggestionsBox.classList.remove('hidden');
                    })
                    .catch(error => console.error('Erreur:', error));
            }
        });