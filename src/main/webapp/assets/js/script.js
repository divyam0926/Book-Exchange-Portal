function validateRegisterForm() {
    const password = document.getElementById('password');
    if (!password || password.value.length < 6) {
        alert('Password must contain at least 6 characters.');
        return false;
    }
    return true;
}

function validateLoginForm() {
    return true;
}