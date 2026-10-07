// Client-Side Validation and Interactivity for Book Exchange Portal

function validateRegisterForm() {
    const fullName = document.querySelector('input[name="fullName"]');
    const email = document.querySelector('input[name="email"]');
    const password = document.getElementById('password');
    const phone = document.querySelector('input[name="phone"]');

    if (fullName && fullName.value.trim().length < 2) {
        alert('Please enter your full name (at least 2 characters).');
        fullName.focus();
        return false;
    }

    if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value.trim())) {
        alert('Please enter a valid email address.');
        email.focus();
        return false;
    }

    if (!password || password.value.length < 6) {
        alert('Password must contain at least 6 characters.');
        if (password) password.focus();
        return false;
    }

    if (phone && phone.value.trim() !== '') {
        const cleaned = phone.value.replace(/[^0-9]/g, '');
        if (cleaned.length < 10) {
            alert('Please enter a valid 10-digit mobile number.');
            phone.focus();
            return false;
        }
    }

    return true;
}

function validateLoginForm() {
    const email = document.querySelector('input[name="email"]');
    const password = document.querySelector('input[name="password"]');

    if (!email || email.value.trim() === '') {
        alert('Please enter your email.');
        return false;
    }

    if (!password || password.value.trim() === '') {
        alert('Please enter your password.');
        return false;
    }

    return true;
}

function openExchangeModal(bookId, bookTitle, ownerName) {
    const modal = document.getElementById('exchangeModal');
    const modalBookId = document.getElementById('modalBookId');
    const modalBookDesc = document.getElementById('modalBookDesc');

    if (modal && modalBookId) {
        modalBookId.value = bookId;
        if (modalBookDesc) {
            modalBookDesc.innerText = `You are requesting "${bookTitle}" listed by ${ownerName || 'the student'}.`;
        }
        modal.classList.add('show');
    }
}

function closeExchangeModal() {
    const modal = document.getElementById('exchangeModal');
    if (modal) {
        modal.classList.remove('show');
    }
}

// Toggle inline exchange drawer on dashboard cards
function toggleExchangeDrawer(id) {
    const drawer = document.getElementById('drawer-' + id);
    if (!drawer) return;
    if (drawer.classList.contains('open')) {
        drawer.classList.remove('open');
    } else {
        document.querySelectorAll('.exchange-drawer').forEach(function(d) {
            d.classList.remove('open');
        });
        drawer.classList.add('open');
        const ta = drawer.querySelector('textarea');
        if (ta) ta.focus();
    }
}