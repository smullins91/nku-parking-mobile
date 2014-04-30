<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="EditUser.aspx.cs" Inherits="ParkingManagement.WebContent.UsersManagement.EditUser" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <script src="../../Scripts/jquery-2.1.0.js"></script>
    <script src="../../Scripts/bootstrap2.js"></script>
    <link href="../../Content/bootstrap2.2.css" rel="stylesheet" />
    <style>
         
         input[type="text"],textarea, input[type="text"], input[type="password"]{margin-top:0px}
        .col-sm-4 {margin-top: 15px; } 
         body { background: #FFF url('/../Images/background.jpg') repeat-x; width:975px; margin-left:auto;
                margin-right:auto
         }
       
         .userErrorMessage{color:red; font-weight:bold;  font-size:12px;  margin-left: 0px; }
         a{color:black}
    </style>
</head>
<body>
    
    <form id="form1" runat="server">
         <div class="header-image" style="margin-left:auto; margin-right:auto; width:970px; ">
             <asp:Image ID="Banner" runat="server" ImageUrl="~/Images/banner.jpg"/>
         </div>
          <div style="margin-top:10px">
             
               <a href="UsersManagement.aspx" style="float:right">Main Menu</a>
               <h5>NKU Parking Management System</h5>
           </div>
       
        <!-- Add new user begins--> 
        <div class="modal fade in" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false"
           >

         <div class="modal-dialog" style="margin-top:0">
            <div class="modal-content">
              <div class="modal-header">
                 <h4 class="modal-title" id="myModalLabel">Users Management >>Edit user</h4>
              </div>
          
                       <div class="modal-body" style="max-height:500px">
                        <div class="form-group">
                            <asp:Label ID="lblRole" runat="server" Text="User Group*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                
                                <asp:DropDownList ID="ddlUserRoles" runat="server" class="form-control">
                                </asp:DropDownList>
                            </div>
                        </div>
                           
                        <div class="form-group">
                            <asp:Label ID="lblFirstName" runat="server" Text="FirstName:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                 <asp:TextBox ID="txtFirstName" runat="server" class="form-control" MaxLength="20" style="margin-top:10px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="rfvTxtFirstName" runat="server" ErrorMessage="First Name is required!" ControlToValidate="txtFirstName" Display="Static" CssClass="userErrorMessage"></asp:RequiredFieldValidator>
                            </div>
                        </div>

                        <div class="form-group">
                            <asp:Label ID="lblLastName" runat="server" Text="Last Name:*" class="col-sm-4 control-label"></asp:Label>
                             <div class="col-sm-8">
                                <asp:TextBox ID="txtLastName" runat="server" class="form-control" MaxLength="20"></asp:TextBox>
                                 <asp:RequiredFieldValidator ID="rfvTxtLastName" runat="server" ErrorMessage="Last Name is required!" ControlToValidate="txtLastName" Display="Static" CssClass="userErrorMessage"></asp:RequiredFieldValidator>
                            </div>
                         </div>

                          <div class="form-group">
                            <asp:Label ID="lblEmail" runat="server" Text="Email:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                <asp:TextBox ID="txtEmail" runat="server" class="form-control" MaxLength="30"></asp:TextBox>
                                 <asp:RequiredFieldValidator ID="rfTxtEmail" runat="server" ErrorMessage="Email is required!" ControlToValidate="txtEmail" Display="Static" CssClass="userErrorMessage"></asp:RequiredFieldValidator>
                             </div>
                         </div>
                        <div class="form-group">
                            <asp:Label ID="lblUserName" runat="server" Text="Username:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                <asp:TextBox ID="txtUserName" runat="server" class="form-control" MaxLength="10"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="rfvTxtUserName" runat="server" ErrorMessage="Username is required!" ControlToValidate="txtUserName" Display="Static" CssClass="userErrorMessage"></asp:RequiredFieldValidator>
                            </div>
                         </div>
                   
                                       <div class="form-group">
                            <asp:CheckBox ID="chkAdmin" runat="server"  class="control-label"/>
                            <asp:Label ID="lblAdministrator" runat="server" Text="Administrator" class="col-sm-4 control-label" style="margin-top:0px"></asp:Label>
                          
                         </div>

           
                          <div class="modal-footer">
                              <asp:Button ID="btnSave" type="button" runat="server" Text="Save" OnClick="btnSave_Click"
                                  class="btn"  />
                             <asp:Button ID="btnCancel"  type="button" runat="server" Text="Cancel" class="btn" onClick="btnCancel_Click" CausesValidation="False" />

                        </div>
                    </div>
                       
                </div> 
            </div> 
        </div>
         
          <!-- Add new user ends-->  
         
    </form>
   
</body>
</html>
