// Password change toggle

const toggleCurrentPassword = document.querySelector("#toggleCurrentPassword");
const toggleNewPassword = document.querySelector("#toggleNewPassword");
const toggleRepeatNewPassword = document.querySelector("#toggleRepeatNewPassword");

const currentPasswordInput = document.querySelector("#currentPasswordInput");
const newPasswordInput = document.querySelector("#newPasswordInput");
const repeatNewPasswordInput = document.querySelector("#repeatNewPasswordInput");

toggleCurrentPassword.addEventListener("click", function () {
    // toggle the type attribute
    const type = currentPasswordInput.getAttribute("type") === "password" ? "text" : "password";
    currentPasswordInput.setAttribute("type", type);

    // toggle the icon
    this.classList.toggle("bi-eye");
});

toggleNewPassword.addEventListener("click", function () {
    // toggle the type attribute
    const type = newPasswordInput.getAttribute("type") === "password" ? "text" : "password";
    newPasswordInput.setAttribute("type", type);

    // toggle the icon
    this.classList.toggle("bi-eye");
});

toggleRepeatNewPassword.addEventListener("click", function () {
    // toggle the type attribute
    const type = repeatNewPasswordInput.getAttribute("type") === "password" ? "text" : "password";
    repeatNewPasswordInput.setAttribute("type", type);

    // toggle the icon
    this.classList.toggle("bi-eye");
});
