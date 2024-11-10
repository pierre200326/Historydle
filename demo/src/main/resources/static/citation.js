const searchInput = document.getElementById('searchInput');
const suggestionsBox = document.getElementById('suggestions');
const validateButton = document.querySelector('button[type="submit"]');
const searchForm = document.getElementById('searchForm');
let currentSuggestions = [];

// Désactive le bouton "Valider" par défaut
validateButton.disabled = true;

// Vérifie si l'URL contient le paramètre 'correct=true'
const urlParams = new URLSearchParams(window.location.search);
if (urlParams.get('correct') === 'true') {
    lancerConfettis();
    searchForm.style.display = 'none'; // Cache la div entière contenant le formulaire
}

searchInput.addEventListener('input', function() {
    const query = this.value;
    suggestionsBox.innerHTML = '';
    suggestionsBox.classList.add('hidden');
    currentSuggestions = []; // Réinitialise les suggestions

    if (query.length > 0) {
        fetch(`/autocomplete?query=${query}`)
            .then(response => response.json())
            .then(data => {
                currentSuggestions = data; // Mémorise les suggestions actuelles
                data.forEach(suggestion => {
                    const suggestionItem = document.createElement('a');
                    suggestionItem.classList.add('list-group-item', 'list-group-item-action', 'hover:bg-blue-200', 'px-4', 'py-2', 'block');
                    suggestionItem.textContent = suggestion;
                    suggestionsBox.appendChild(suggestionItem);

                    // Lorsqu'un élément est cliqué, remplit l'input avec la suggestion et cache la liste
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
        validateButton.disabled = true; // Désactive le bouton si l'input est vide
    }
});

// Vérifie l'input lors de chaque modification pour activer/désactiver le bouton
searchInput.addEventListener('input', function() {
    validateButton.disabled = !currentSuggestions.includes(searchInput.value);
});

function lancerConfettis() {
    confetti({
        particleCount: 100,
        spread: 70,
        origin: { y: 0.6 }
    });
}
