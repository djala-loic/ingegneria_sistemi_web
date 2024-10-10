<style>
    *,body{
        font-family: 'Poppins', sans-serif;
        box-sizing: border-box;
        padding: 0;
        font-size: 16px;
        margin: 0;
    }
    body{
        background: #F6F4F5;
        color: #193354;
    }
    mark{
        font-size: 10px!important;
    }


    .container {
        width: 70%;
        margin: 0 auto;
        height: auto;
        background: white;
    }

    .container .links {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333;

    }

    .container ul li {
        float: left;
    }

    .container ul li a, .link {
        display: block;
        color: white;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
    }

    .container ul li a:hover:not(.active) {
        background-color: #111;
    }

    .active {
        background-color: #04AA6D;
    }


    .hero{
        width: 100%;
        height: auto;
        padding: 1rem;
        background-color: #e9e9e9;
    }

    .hero .intro{
        width: 100%;
    }

    .hero .intro .name{
        font-weight: 600;
        text-transform: capitalize;
    }

    .separator{
        border: 2px solid #383D3B;
        width: 100%;
    }
    .hero .intro p{
        width: 100%;
        font-size: 38px;
        font-weight: 600;
    }

    .hero .intro p .logo{
        font-weight: 800 !important;
        color: #52DEE5;
        text-decoration: underline;
    }

    .hero p {
        width: 100%;
        text-align: left;
        font-size: 20px;
        font-weight: 400;
    }


    .container .user_dati{
        width: 100%;
        margin-bottom: 1rem;
        padding: 1rem;
    }

    .container .user_dati .info,
    .container .user_dati .dati {
        width: 100%;
        margin-bottom: 1rem;
    }


    .container .user_dati .info h1 {
        font-weight: bold;
        text-align: left;
        text-decoration: underline;
    }

    .container .user_dati .dati ol{
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;
        gap: 1rem;
    }
    .container .user_dati .dati ol,
    .container .user_dati .dati ol li,
    .container .user_dati .dati ol li .item{
        width: 100%;
    }


    .container .user_dati .dati ol li{
        border: 1px solid #333;
        padding: .5rem 1rem;
        border-radius: 10px;
        list-style: inside;
    }

    .container .user_dati .dati ol li:last-child{
        margin-bottom: 0;
    }

    .container .user_dati .dati ol li .item{
        display: flex;
        justify-content: flex-start;
        align-items: flex-start;
        flex-direction: column;
    }
    .container .user_dati .dati ol li .item h3{
        font-weight: bold;
        margin-bottom: 0.3rem;
        color: #383D3B;
        text-decoration: underline;
    }

    .container .user_dati .dati ol li .item p{
        font-weight: 500;
        margin-bottom: 0.3rem;
        color: #111111;
        text-transform: uppercase;
    }

    .container .user_dati .dati ol li .item .modifica_botton{
        width: 100%;
        font-weight: 500;
        margin-bottom: 0.3rem;
        color: #111111;
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    a{
        color: #383D3B;
        transition: all 0.3s ease-in-out;

    }
    a:hover{
        color: #52DEE5;
        text-decoration: underline;
    }
</style>

<link href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">

