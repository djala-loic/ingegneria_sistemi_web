<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script defer src="https://use.fontawesome.com/releases/v6.1.1/js/all.js" integrity="sha384-xBXmu0dk1bEoiwd71wOonQLyH+VpgR1XcDH3rtxrLww5ajNTuMvBdL5SOiFZnNdp" crossorigin="anonymous"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.11.0/styles/default.min.css">
<link href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">

<style>
    *,body{
        font-size: 16px;
        font-family: 'Poppins', sans-serif;
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    p,span,button{
        font-size: 15px !important;
    }
    mark{
        font-size: 12px!important;
    }


    .GestionDati .info_title{
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
    }
    .GestionDati h1{
        font-weight: bold;
        font-size: 2rem;
    }

    .GestionDati p{
        font-size: 1rem;
    }

    .GestionDati p b{
        font-style: italic;
    }

    .GestionDati .tabella{
        margin: 2rem 0;
    }

    .tabella  .usersGroup{
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 1rem;
    }
    .tabella .usersGroup,
    .tabella .usersGroup .userItem,
    .tabella .usersGroup .userItem .item{
        width: 100%;
    }

    .tabella .usersGroup .userItem .item ul li {
        border: none;
    }

    .tabella .usersGroup .userItem .item ul {
        display: flex;
        flex-direction: column;
    }

    .tabella .usersGroup .userItem{
        border: 1px solid #333;
        padding: .5rem 1rem;
        border-radius: 10px;
        list-style: inside;
        display: flex;
        flex-direction: column;

    }

    .tabella .usersGroup .userItem:last-child{
        margin-bottom: 0;
    }

    .tabella .usersGroup .UsersOption{
        border-top: 2px  solid #193354;
        display: flex;
        justify-content: space-between;
        margin-top: 1rem;
        padding: 1rem 0;
    }

    .tabella .usersGroup .UsersOption .left{
        display: flex;
        justify-content: left;
    }

    .tabella .usersGroup .UsersOption span {
        margin-right: 1rem;
        transition: all 0.1s ease-in-out;
        border: 1px solid #193354;
        border-radius: 4px;
        font-size: 13px;
        color: white;
        font-weight: 500;
        padding: 0.2rem 1rem;
        cursor: pointer;
    }

    .vert{
        background: #04AA6D !important;

    }

     .red{
        background: #511730 !important;

    }

     .redFonce{
        background: #D63230 !important;

    }


    .tabella .usersGroup .UsersOption span:hover {
        transform:scale(0.98);
    }

    .tabella .usersGroup .UsersOption span:last-child {
        margin-right: 0;
    }




    .GestionDati .search-container {
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .GestionDati input[type=text] {
        padding: 6px;
        margin-top: 8px;
        font-size: 17px;
        border: 1px solid #193354 !important;
    }

    .GestionDati .search-container button {
        float: right;
        padding: 6px 10px;
        margin-top: 8px;
        margin-right: 16px;
        background: #ddd;
        font-size: 17px;
        border: none;
        cursor: pointer;
    }

    .GestionDati .search-container button:hover {
        background: #ccc;
    }

    .GestionDati .search-container select {
        padding: 6px !important;
        margin-top: 8px !important;
        font-size: 17px !important;
        border: 1px solid #193354 !important;
        margin-right: 10px;
    }

    @media screen and (max-width: 600px) {
        .GestionDati .search-container {
            float: none;
        }
        .GestionDati input[type=text], .GestionDati .search-container button {
            float: none;
            display: block;
            text-align: left;
            width: 100%;
            margin: 0;
            padding: 14px;
        }
        .GestionDati input[type=text] {
            border: 1px solid #ccc;
        }
    }

</style>