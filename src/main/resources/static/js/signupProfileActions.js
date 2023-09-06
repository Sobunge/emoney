
// Role selection toggle

const adminInput = document.getElementById("ADMIN");
const landlordInput = document.getElementById("LANDLORD")
const tenantInput = document.getElementById("TENANT");
const userInput = document.getElementById("USER");

adminInput.addEventListener("change", adminRoleToggle);
landlordInput.addEventListener("change", landlordRoleToggle);
tenantInput.addEventListener("change", tenantRoleToggle);
userInput.addEventListener("change", userRoleToggle);

function adminRoleToggle() {

    if (adminInput.checked) {
        document.getElementById("LandlordInput").style.display = "none";
        landlordInput.checked = false;
        document.getElementById("TenantInput").style.display = "none";
        tenantInput.checked = false;
        document.getElementById("UserInput").style.display = "none";
        userInput.checked = false;
    } else {
        document.getElementById("LandlordInput").style.display = "";
        document.getElementById("TenantInput").style.display = "";
        document.getElementById("UserInput").style.display = "";
    }

}

function landlordRoleToggle() {

    if (landlordInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
        document.getElementById("TenantInput").style.display = "none";
        tenantInput.checked = false;
    } else {
        if (userInput.checked) {
            document.getElementById("AdminInput").style.display = "none";
        } else {
            document.getElementById("AdminInput").style.display = "";
        }
        document.getElementById("TenantInput").style.display = "";
    }

}

function tenantRoleToggle() {

    if (tenantInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
        document.getElementById("LandlordInput").style.display = "none";
        landlordInput.checked = false;
    } else {
        if (userInput.checked) {
            document.getElementById("AdminInput").style.display = "none";
        } else {
            document.getElementById("AdminInput").style.display = "";
        }
        document.getElementById("LandlordInput").style.display = "";
    }
}

function userRoleToggle() {

    if (userInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
    } else {
        if (tenantInput.checked || landlordInput.checked) {
            document.getElementById("AdminInput").style.display = "none";
        } else {
            document.getElementById("AdminInput").style.display = "";
        }
    }
}

// Checking validity of phonenumber entry

const phoneNumberInput = document.getElementById("phoneNumberInput");
phoneNumberInput.addEventListener("change", isPhoneNumberValid);

function isPhoneNumberValid() {

    let phoneNumber = phoneNumberInput.value;

    if ((phoneNumber >= 100000000 && phoneNumber <= 199999999) || (phoneNumber >= 700000000 && phoneNumber <= 799999999)) {
        phoneNumberInput.setCustomValidity("");
    } else {
        phoneNumberInput.setCustomValidity("Invalid phone number");
    }

}

// Checking validity of idnumber entry

const idNumberInput = document.getElementById("idNumberInput");
idNumberInput.addEventListener("change", isIdNumberValid);

function isIdNumberValid() {

    let idNumber = idNumberInput.value;

    if (idNumber >= 100000 && idNumber <= 99999999) {
        idNumberInput.setCustomValidity("");
    } else {
        idNumberInput.setCustomValidity("Invalid Id number");
    }
}