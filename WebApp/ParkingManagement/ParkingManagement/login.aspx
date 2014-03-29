<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="login.aspx.cs" Inherits="ParkingManagement.login" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>NKU Parking Mnagement System--Login</title>
    <link href="Content/bootstrap2.2.css" rel="stylesheet" />
     <style type="text/css">

        body
        {
            width:520px;
            margin:100px auto;
            background: #FFF url('../Images/background.jpg') repeat-x;
  
        }
        h3
        {
            text-align:center;
        }
       #login
        {
            width:400px;
             margin:0px auto;
            border:1px solid black;
            border-radius:5px;
            padding:10px;
       
        }
       label
        {
            width:90px;
       }

        input
        {
            width:60%;
           
        }
        .form-group
        {
            margin-top:10px;
        }
        #lblError
        {
            color:red;
            font-weight:bold;
        }
        p
        {
            font-weight:bold;
            font-size:10px;
            margin-top:10px;
            text-align:center;
        }
        .errorMessage
        {
            color:red;
            font-weight:bold;
            font-size:12px;
            margin-left: 100px;
        }
    </style>
</head>
<body>

    <h3>NKU Parking Management System</h3>
    <div id="login">
        <form id="form1" runat="server" class="form-inline" role="form">

            <asp:Label ID="lblError" runat="server" Text="Invalid Username or Password!" Visible="false"></asp:Label>
            <div class="form-group">
               <label class="sr-only">*Username:</label>
                <asp:TextBox ID="txtUsername" runat="server" class="form-control" placeholder="Username" ></asp:TextBox>
                <asp:RequiredFieldValidator ID="rfvUserName" runat="server" ErrorMessage="RequiredFieldValidator" ControlToValidate="txtUserName" Text="Username is required!" CssClass="errorMessage"></asp:RequiredFieldValidator>

             </div>

            <div class="form-group">
                <label class="sr-only">*Password:</label>
                <asp:TextBox ID="txtPassword" runat="server" class="form-control" placeholder="Password" TextMode="Password" ControlToValidate="txtPassword"></asp:TextBox>
                <asp:RequiredFieldValidator ID="rfvPassword" runat="server" ErrorMessage="RequiredFieldValidator" Display="Dynamic" Text="Password is required!" CssClass="errorMessage" ControlToValidate="txtPassword"></asp:RequiredFieldValidator>
            </div>
            <div style="clear:none"></div> 
            <div style="width:100%; text-align:center;margin-top:20px;">
                 <asp:Button ID="btnLogin" runat="server" Text="Sign in"  class="btn"  OnClick="btnLogin_Click"
                    />
   
            </div>
               
                
           
        </form>
       
    </div>
     <p>&copy; <%: DateTime.Now.Year %> - Team 3 NKU Parking Managemnt Application</p>
    
</body>
</html>
