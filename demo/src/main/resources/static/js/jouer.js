const searchInput = document.getElementById('searchInput');
const suggestionsBox = document.getElementById('suggestions');
const validateButton = document.querySelector('button[type="submit"]');
const searchForm = document.getElementById('searchForm');
let currentSuggestions = [];

if (searchForm===null){
    lancerConfettis();
}
else{
    // Désactive le bouton "Valider" par défaut
    validateButton.disabled = true;
}

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
                    const suggestionItem = document.createElement('div');
                    suggestionItem.classList.add('flex', 'items-center', 'space-x-2', 'list-group-item', 'list-group-item-action', 'hover:bg-blue-200', 'px-4', 'py-2', 'block');

                    // Créer une image en utilisant le nom du personnage
                    const img = document.createElement('img');
                    img.src = `/Historydle/${suggestion}.webp`;
                    img.alt = `Image of ${suggestion}`;
                    img.classList.add('w-12', 'h-12');

                    // Ajouter le texte de suggestion
                    const text = document.createElement('span');
                    text.textContent = suggestion;

                    // Ajouter l'image et le texte à l'élément de suggestion d
                    suggestionItem.appendChild(img);
                    suggestionItem.appendChild(text);
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

// Function to add bounce animation with staggered timing to the last row of the results table
function addBounceAnimation() {
    const resultsTable = document.getElementById('resultsTable');
    if (resultsTable) {
        const rows = resultsTable.querySelectorAll('tr');
        if (rows.length > 0) {
            const lastRow = rows[0];
            const cells = lastRow.querySelectorAll('td');
            
            cells.forEach((cell, index) => {
                setTimeout(() => {
                    cell.classList.add('animate-bounce');

                    setTimeout(() => {
                        cell.classList.remove('animate-bounce');
                    }, 1000); 
                }, index * 100); 
            });
        }
    }
}

// Call the function when needed, for example, after adding a new row
document.addEventListener('DOMContentLoaded', () => {
    // Example: Simulate adding a row and then call the function
    addBounceAnimation();
});

