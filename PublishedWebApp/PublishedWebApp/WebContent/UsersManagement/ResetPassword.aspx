<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="ResetPassword.aspx.cs" Inherits="ParkingManagement.WebContent.UsersManagement.ResetPassword" %>

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
    
        <div class="modal fade in" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false"
           >

         <div class="modal-dialog" style="margin-top:0">
            <div class="modal-content">
              <div class="modal-header">
                 <h4 class="modal-title" id="myModalLabel">Users Management >>Reset Password</h4>
              </div>
          
                       <div class="modal-body" style="max-height:500px">
                        <div class="form-group">
                            <asp:Label ID="lblNewPassword1" runat="server" Text="New Password:*" class="col-sm-4 control-label"></asp:Label>
                             <div class="col-sm-8">
                                <asp:TextBox ID="txtPassword1" runat="server" class="form-control" TextMode="Password"></asp:TextBox>
                                 <asp:RequiredFieldValidator ID="rfvTxtPassword1" runat="server" ErrorMessage="Password is required!" ControlToValidate="txtPassword1" CssClass="userErrorMessage"></asp:RequiredFieldValidator>
                            </div>
                         </div>

                        <div class="form-group">
                            <asp:Label ID="lblConfPassword1" runat="server" Text="Confirm Password:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                <asp:TextBox ID="txtConfPassword1" runat="server" class="form-control" TextMode="Password"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="rfvTxtConfPassword1" runat="server" ErrorMessage="Password is required!" ControlToValidate="txtConfPassword1" CssClass="userErrorMessage"></asp:RequiredFieldValidator>
                                <asp:CompareValidator ID="cvTxtConfPassword1" runat="server" ErrorMessage="Passwords do not match!" ControlToValidate="txtConfPassword1" ControlToCompare="txtPassword1" CssClass="userErrorMessage"></asp:CompareValidator>
                            </div>
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
         
         
         
    </form>
   
</body>
</html>
