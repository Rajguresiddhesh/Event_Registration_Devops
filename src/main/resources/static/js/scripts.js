// College Event Registration Platform - Frontend Script
// Student: 23102A0003 SIDDHESH RAJGURE

document.addEventListener('DOMContentLoaded', () => {
    // Event Search Filter on Homepage
    const searchInput = document.getElementById('eventSearch');
    if (searchInput) {
        searchInput.addEventListener('input', (e) => {
            const term = e.target.value.toLowerCase();
            const cards = document.querySelectorAll('.event-card');
            cards.forEach(card => {
                const title = card.querySelector('.event-title')?.textContent.toLowerCase() || '';
                const desc = card.querySelector('.event-desc')?.textContent.toLowerCase() || '';
                const category = card.querySelector('.event-badge')?.textContent.toLowerCase() || '';
                if (title.includes(term) || desc.includes(term) || category.includes(term)) {
                    card.style.display = 'flex';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    }

    // Client-Side Live Form Validation on Register Page
    const form = document.getElementById('registrationForm');
    if (form) {
        const rollInput = document.getElementById('studentRollNumber');
        const phoneInput = document.getElementById('studentPhone');
        const emailInput = document.getElementById('studentEmail');

        if (rollInput) {
            rollInput.addEventListener('input', () => {
                rollInput.value = rollInput.value.toUpperCase();
            });
        }

        form.addEventListener('submit', (e) => {
            let valid = true;

            if (rollInput && !/^[A-Za-z0-9]{5,20}$/.test(rollInput.value.trim())) {
                alert('Roll number must be alphanumeric (5 to 20 characters).');
                rollInput.focus();
                e.preventDefault();
                valid = false;
            }

            if (valid && phoneInput && !/^[0-9+()\- ]{7,20}$/.test(phoneInput.value.trim())) {
                alert('Please enter a valid phone number (minimum 7 digits).');
                phoneInput.focus();
                e.preventDefault();
                valid = false;
            }
        });
    }

    // Print Pass Helper
    const printBtn = document.getElementById('printPassBtn');
    if (printBtn) {
        printBtn.addEventListener('click', () => {
            window.print();
        });
    }
});
