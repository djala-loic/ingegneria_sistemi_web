<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script defer src="https://use.fontawesome.com/releases/v6.1.1/js/all.js" integrity="sha384-xBXmu0dk1bEoiwd71wOonQLyH+VpgR1XcDH3rtxrLww5ajNTuMvBdL5SOiFZnNdp" crossorigin="anonymous"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.11.0/styles/default.min.css">
<link href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">

<style>

    *,body{
        font-family: 'poppins', sans-serif;
        box-sizing: border-box;
        padding: 0;
        margin: 0;
        font-size: 16px;
    }
    p,span,button{
        font-size: 15px !important;
    }

    mark{
        font-size: 12px!important;
    }

    .topnav {
        overflow: hidden;
        background-color: #e9e9e9;
        color: white !important;
    }

    .topnav h1 {
        float: left;
        display: block;
        color: black;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
        font-size: 30px;
    }

    .topnav .search-container {
        float: right;
    }

    .topnav input[type=text] {
        padding: 6px;
        margin-top: 8px;
        font-size: 17px;
        border: none;
    }

    .topnav .search-container button {
        float: right;
        padding: 6px 10px;
        margin-top: 8px;
        margin-right: 16px;
        background: #ddd;
        font-size: 17px;
        border: none;
        cursor: pointer;
    }

    .topnav .search-container button:hover {
        background: #ccc;
    }



    @media screen and (max-width: 600px) {
    .topnav .search-container {
            float: none;
        }
    .topnav h1, .topnav input[type=text], .topnav .search-container button {
            float: none;
            display: block;
            text-align: left;
            width: 100%;
            margin: 0;
            padding: 14px;
        }
    .topnav input[type=text] {
            border: 1px solid #ccc;
        }
    }

    .container .templates_dati{
        width: 100%;
        margin-bottom: 1rem;
        padding: 1rem;
    }

    .container .templates_dati .info {
        width: 100%;
        margin-bottom: 1rem;
    }

    .container .templates_dati .info .info_title{
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
    }

    .container .templates_dati .info h1 {
        font-weight: bold;
        text-decoration: underline;
    }

    .container .templates_dati .dati ol{
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 1rem;
    }
    .container .templates_dati .dati ol,
    .container .templates_dati .dati ol li,
    .container .templates_dati .dati ol li .item{
        width: 100%;
    }


    .container .templates_dati .dati ol li{
        border: 1px solid #333;
        padding: .5rem 1rem;
        border-radius: 10px;
        list-style: inside;
    }

    .container .templates_dati .dati ol li:last-child{
        margin-bottom: 0;
    }

    .container .templates_dati .dati ol li .item{
        display: flex;
        justify-content: flex-start;
        align-items: flex-start;
        flex-direction: column;
    }
    .container .templates_dati .dati ol li .item h3{
        font-weight: bold;
        margin-bottom: 0.3rem;
        color: #383D3B;
        text-decoration: underline;
    }

    .container .templates_dati .dati ol li .item p{
        font-weight: 500;
        margin-bottom: 0.3rem;
        color: #111111;
        text-transform: uppercase;
    }

    .container .templates_dati .dati ol li .item .option{
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .container .templates_dati .dati ol li .item .option .isUserOption{
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .container .templates_dati .dati ol li .item .option  button{
        margin-right: 5px;
        font-size: 18px;
    }

    .red{
        color: red !important;
        transition: all 0.3s ease-in-out;
    }

    .vert {
        color: #04AA6D !important;
        transition: all 0.3s ease-in-out;
    }

    .search-container{
        display: flex;
        justify-content: space-between;
        align-items: center;
    }



    .search-container select {
        padding: 6px !important;
        margin-top: 8px !important;
        font-size: 17px !important;
        border: none !important;
        margin-right: 10px;
    }


    .blue{
        background: transparent !important;
        color: lightskyblue !important;
        margin-right: 1rem !important;
        transition: all 0.3s ease-in-out;
    }
    .blue:hover{
        color: #111111 !important;
        border: none !important;
    }

    .view{
        background:  transparent !important;
        color: #111111 !important;
        transition: all 0.3s ease-in-out;
        padding: 2px;
    }
    .view:hover{
        background: #111111 !important;
        color: #ffffff !important;
        border: none !important;
    }

    textarea{
        font-size: 18px;
        color: #383D3B;
        width: 100%;
        min-height: 120px;
        border: 1px solid #193354;
        border-radius: 3px;
        padding: 6px;
        transition: all 0.2s ease-in-out;
    }

    select{
        padding: 10px;
        font-size: 18px;
        border: 1px solid #193354;
        border-radius: 3px;
        transition: all 0.2s ease-in-out;
        color: #383D3B;
    }

    .one_container{
        width: 100%;
        padding: 1rem;
    }

    .one_container .map{
        display: flex;
        justify-content: flex-start;
        align-items: center;
        margin-bottom: 1rem;
    }
    .one_container a, .one_container span{
        margin-right: 0.5rem !important;
        font-size: 13px !important;
    }

    .one_container .title_info{
        width: 100%;
        text-align: left;
    }

    .one_container .fotos .fotoGrid{
        width: 100%;
        display: grid;
        grid-template-columns: 1fr;
        gap: 1rem;
    }
    .one_container .fotos .fotoGrid .item_foto{
        height: 300px;
        background: #fcf6f6;
        border-radius: 10px;
        border: 2px solid;
        position: relative;
        backdrop-filter: none;
    }

    .one_container .fotos .fotoGrid .item_foto img{
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .one_container .fotos .fotoGrid .item_foto button {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        border: 2px solid #000;
        border-radius: 2px;
        font-size: 18px;
        font-weight: bold;
        backdrop-filter: blur(5px);
        background: transparent;
        padding: 0.2rem 2rem;
    }



    @media only screen and (min-width: 650px) {
        .container {
            width: 90%;
        }

        .one_container .fotos .fotoGrid{
            width: 100%;
            grid-template-columns: 1fr;
        }
    }

    @media only screen and (min-width: 850px) {
        .container {
            width: 80%;
        }

        .one_container .fotos .fotoGrid{
            grid-template-columns: 1fr 1fr !important;
        }
    }

    @media only screen and (min-width: 1200px) {
        .container {
            width: 70%;
        }
        .one_container .fotos .fotoGrid{
            grid-template-columns: 1fr 1fr 1fr !important;
        }
    }

    .container .form_container,.container .form_container .sub_form_container{
        width: 100% !important;
    }


    .blockMessage {
        width: 400px;
        padding: 10px;
        margin: 0 auto;
    }

    .message-blue {
        position: relative;
        margin-left: 20px;
        margin-bottom: 10px;
        padding: 10px;
        background-color: #A8DDFD;
        width: 200px;
        height: 50px;
        text-align: left;
        font: 400 .9em 'Open Sans', sans-serif;
        border: 1px solid #97C6E3;
        border-radius: 10px;
    }

    .message-orange {
        position: relative;
        margin-bottom: 10px;
        margin-left: calc(100% - 240px);
        padding: 10px;
        background-color: #f8e896;
        width: 200px;
        height: 50px;
        text-align: left;
        font: 400 .9em 'Open Sans', sans-serif;
        border: 1px solid #dfd087;
        border-radius: 10px;
    }

    .message-content {
        padding: 0;
        margin: 0;
    }

    .message-timestamp-right {
        position: absolute;
        font-size: .85em;
        font-weight: 300;
        bottom: 5px;
        right: 5px;
    }

    .message-timestamp-left {
        position: absolute;
        font-size: .85em;
        font-weight: 300;
        bottom: 5px;
        left: 5px;
    }

    .message-blue:after {
        content: '';
        position: absolute;
        width: 0;
        height: 0;
        border-top: 15px solid #A8DDFD;
        border-left: 15px solid transparent;
        border-right: 15px solid transparent;
        top: 0;
        left: -15px;
    }

    .message-blue:before {
        content: '';
        position: absolute;
        width: 0;
        height: 0;
        border-top: 17px solid #97C6E3;
        border-left: 16px solid transparent;
        border-right: 16px solid transparent;
        top: -1px;
        left: -17px;
    }

</style>