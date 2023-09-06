// Password change toggle
const newPasswordInput = document.getElementById("newPasswordInput");
const repeatNewPasswordInput = document.getElementById("repeatNewPasswordInput");
repeatNewPasswordInput.addEventListener("change", repeatNewPasswordToggle);

function repeatNewPasswordToggle() {

    if (newPasswordInput.value == repeatNewPasswordInput.value) {
        newPasswordInput.setCustomValidity("");
        repeatNewPasswordInput.setCustomValidity("");
    } else {
        newPasswordInput.setCustomValidity("New password does not match repeat password");
        repeatNewPasswordInput.setCustomValidity("Repeat password does not match new password");

        alert("Your new password and repeat password does not match.");
    }

}

