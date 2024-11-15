const searchInput = document.getElementById('searchInput');
const suggestionsBox = document.getElementById('suggestions');
const validateButton = document.querySelector('button[type="submit"]');
let currentSuggestions = [];

// Désactive le bouton "Valider" par défaut
validateButton.disabled = true;

searchInput.addEventListener('input', function() {
    const query = this.value;
    suggestionsBox.innerHTML = '';
    suggestionsBox.classList.add('hidden');
    currentSuggestions = []; // Réinitialise les suggestions

    if (query.length > 0) {
        fetch(`/autocompletePortrait?query=${query}`)
            .then(response => response.json())
            .then(data => {
                currentSuggestions = data; // Mémorise les suggestions actuelles
                data.forEach(suggestion => {
                    const suggestionItem = document.createElement('a');
                    suggestionItem.classList.add('list-group-item', 'list-group-item-action', 'hover:bg-blue-200', 'px-4', 'py-2', 'block');
                    suggestionItem.textContent = suggestion;
                    suggestionsBox.appendChild(suggestionItem);

                    // Lorsqu'une suggestion est cliquée, remplit l'input et cache les suggestions
                    suggestionItem.addEventListener('click', function() {
                        searchInput.value = suggestion;
                        validateButton.disabled = false; // Active le bouton après sélection d'une suggestion
                        suggestionsBox.innerHTML = '';
                        suggestionsBox.classList.add('hidden');
                    });
                });
                suggestionsBox.classList.remove('hidden');
            })
            .catch(error => console.error('Erreur:', error));
    } else {
        validateButton.disabled = true; // Désactive le bouton si l'input est vide  d
    }
});

// Vérifie l'input lors de chaque modification pour activer/désactiver le bouton
searchInput.addEventListener('input', function() {
    validateButton.disabled = !currentSuggestions.includes(searchInput.value);
});
