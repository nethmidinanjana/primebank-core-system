document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('customerForm');

    if (!form) return;

    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const formData = new FormData(form);

        try {
            const response = await fetch(`${contextPath}/api/customers/save`, {
                method: 'POST',
                body: formData
            });

            const result = await response.json();

            if (result.status) {
                window.location.href = `${contextPath}/teller/dashboard.jsp`;
            } else {
                const errorBox = document.getElementById('error-message');
                errorBox.textContent = result.message || "An error occurred.";
                errorBox.style.display = 'block';
            }
        } catch (err) {
            console.error("AJAX error", err);
            const errorBox = document.getElementById('error-message');
            errorBox.textContent = "Something went wrong. Please try again.";
            errorBox.style.display = 'block';
        }
    });
});

async function createAccount(){

    const customerSearch = document.getElementById("customerSearch").value.trim();
    const accountType = document.getElementById("accountType").value;
    const initialDeposit = document.getElementById("initialDeposit").value.trim();
    const guardianEmail = document.getElementById("guardianEmail").value.trim();

    const data = {
        customerIdentifier: customerSearch,
        accountType: accountType,
        initialDeposit: parseFloat(initialDeposit),
        guardianEmail: guardianEmail || null
    }

    try {

        const response = await fetch(`${contextPath}/api/accounts/create`,{
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (result.status) {
            alert("✅ Account created successfully");
            window.location.href = `${contextPath}/teller/dashboard.jsp`;
        } else {
            alert("❌ " + result.message);
        }
    }catch (error) {
        console.error("Error creating account:", error);
        alert("❌ Something went wrong while creating the account.");
    }
}