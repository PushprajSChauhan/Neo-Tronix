<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="footer.css">
</head>
<body>
    <hr>
    <div class="container-fluid p-3">
        <div class="container">
            <h1 class="text-center gold-text">Contact</h1>
            <p class="text-center teal-text" style="font-size: 1.2rem">We love our fans</p>
            <div class="row">
                <div class="col-md-5">
                    <p class="m-4">
                        <i class="fa-solid fa-phone teal-icon"></i> Mob: +91 8269559239
                    </p>
                    <p class="m-4">
                        <i class="fa-solid fa-envelope teal-icon"></i> Email: chauhanpushprajsingh003@gmail.com
                    </p>
                    <p class="m-4">
                        <i class="fa-solid fa-signs-post teal-icon"></i> Postal Code: 462022
                    </p>
                </div>

                <div class="col-md-7">
                    <form action="" name="submit-to-google-sheet">
                        <div class="row">
                            <div class="col-sm-6 form-group">
                                <input type="text" name="Name" class="form-control" placeholder="Name" required>
                            </div>
                            <div class="col-sm-6 form-group">
                                <input type="email" name="Email" class="form-control" placeholder="Email" required>
                            </div>
                        </div>
                        <textarea name="Message" rows="5" class="form-control mt-3" placeholder="Enter your message"></textarea>

                        <div class="row text-center mt-3">
                            <div class="col-md-12 form-group">
                                <button type="submit" class="btn btn-primary send-button">Send</button>
                            </div>
                            <span id="msg"></span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        const scriptURL = 'https://script.google.com/macros/s/AKfycbyyGJ24hmhCXUri9BhTQRRTJpHKSwinTqKj-ZnZ8QiigPDcsWLFJ0sW8osvR_peWxmPYw/exec'
        const form = document.forms["submit-to-google-sheet"]
        const msg = document.getElementById("msg");

        form.addEventListener('submit', e => {
            e.preventDefault();
            fetch(scriptURL, {method: 'POST', body: new FormData(form)})
                    .then(response => {
                        msg.innerHTML = "Message Sent Successfully!";
                        setTimeout(function () {
                            msg.innerHTML = "";
                        }, 5000);
                        form.reset();
                    })
                    .catch(error => console.error('Error!', error.message));
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>