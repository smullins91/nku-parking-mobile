<%@ Page Title="Log in" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="ParkingManagement.Account.Login" %>
<%@ Register Src="~/Account/OpenAuthProviders.ascx" TagPrefix="uc" TagName="OpenAuthProviders" %>

<asp:Content runat="server" ID="BodyContent" ContentPlaceHolderID="MainContent">
   <div id="form1" runat="server" class="form-horizontal">

        <h2>Please login</h2>
        <div class="form-group">
            <asp:Label ID="lblUserName" runat="server" Text="Username:" class="col-sm-2 control-label"></asp:Label>
            <div class="col-sm-6">
                <asp:TextBox ID="txtUserName" runat="server" class="form-control"></asp:TextBox> 
            </div>
        </div>

        <div class="form-group">
            <asp:Label ID="lblPassword" runat="server" Text="Password:" class="col-sm-2 control-label"></asp:Label>
            <div class="col-sm-6">
                <asp:TextBox ID="txtPassword" runat="server" class="form-control"></asp:TextBox> 
            </div>
        </div>
           

        <div class="form-group">
        <div class="col-sm-offset-8 col-sm-4">
            <input type="submit" class="btn login-button" value="Login" />
        </div>
    </div>
  
    </div>
</asp:Content>


