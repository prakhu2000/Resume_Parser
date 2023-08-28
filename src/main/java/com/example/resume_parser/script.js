document.getElementById('upload-form').addEventListener('submit', async function(event) {
    event.preventDefault();
    
    const formData = new FormData(this);
    const response = await fetch('/upload', {
        method: 'POST',
        body: formData,
    });
    
    const data = await response.json();
    const emailList = document.getElementById('email-list');
    emailList.innerHTML = '';

    data.emails.forEach(email => {
        const listItem = document.createElement('li');
        listItem.textContent = email;
        emailList.appendChild(listItem);
    });
});
