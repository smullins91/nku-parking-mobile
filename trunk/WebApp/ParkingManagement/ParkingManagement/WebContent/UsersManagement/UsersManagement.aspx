<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeBehind="UsersManagement.aspx.cs" Inherits="ParkingManagement.WebContent.UsersManagement.UsersManagement" %>

<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     
     <link href="../../Content/bootstrap.css" rel="stylesheet" />
     <section class="featured">
         <div class="content-wrapper">
             <div style="margin-bottom:60px">
                 <h4>Users Management</h4>
                 <a data-toggle="modal" href="#addModal" class="btn btn-primary btn-lg" 
                     style="float:right; margin-right:20px; font-weight:bold; color:white ">Add New User</a>
             </div>

         <!-- GridView begins-->  
             <asp:UpdatePanel ID="upUsers" runat="server">
                 <ContentTemplate>
                     <asp:GridView ID="gvUsers" runat="server" HorizontalAlign="Center" Width="100%" AutoGenerateColumns="false"
                          AllowPaging="true" CssClass="table table-hover table-striped">
                         <Columns>
                              <asp:BoundField DataField="UserName" HeaderText="UserName" />
                              <asp:BoundField DataField="LastName" HeaderText="Last Name" />
                              <asp:BoundField DataField="FirstName" HeaderText="First Name" />
                              <asp:BoundField DataField="Email" HeaderText="Email" />
                              <asp:BoundField DataField="Role" HeaderText="Role" />
                              <asp:ButtonField CommandName="editUser" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Edit" HeaderText="Edit User">
                                <ControlStyle CssClass="btn btn-primary btn-lg"></ControlStyle>
                              </asp:ButtonField>
                             <asp:ButtonField CommandName="deleteUser" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Delete" HeaderText="Delete User">
                                <ControlStyle CssClass="btn btn-primary btn-lg"></ControlStyle>
                            </asp:ButtonField>
                             <asp:ButtonField CommandName="resetPassword" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Reset Password" HeaderText="Reset Password">
                                <ControlStyle CssClass="btn btn-primary btn-lg"></ControlStyle>
                            </asp:ButtonField>
                         </Columns>

                     </asp:GridView>

                 </ContentTemplate>
             </asp:UpdatePanel>
            <!-- GridView ends-->  

           <!-- Add new user begins--> 
        <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >

         <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add a new user</h4>
              </div>

           <div class="modal-body">

                     
                        <div class="form-group">
                            <asp:Label ID="lblRole" runat="server" Text="Is the user a:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                
                                <asp:DropDownList ID="ddlUserRoles" runat="server" class="form-control">
                                </asp:DropDownList>
                            </div>
                        </div>

                        <div class="form-group">
                            <asp:Label ID="lblUserName" runat="server" Text="Username:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                <asp:TextBox ID="txtUserName" runat="server" class="form-control"></asp:TextBox>
                            </div>
                         </div>

                        <div class="form-group">
                         <asp:Label ID="lblPassword" runat="server" Text="Password:*" class="col-sm-4 control-label"></asp:Label>
                          <div class="col-sm-8">
                            <asp:TextBox ID="txtPassword" runat="server" TextMode="Password" class="form-control"></asp:TextBox>
                          </div>
                        </div>

                        <div class="form-group">
                            <asp:Label ID="lblEmail" runat="server" Text="Email:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                <asp:TextBox ID="txtEmail" runat="server" class="form-control"></asp:TextBox>
                             </div>
                         </div>
                             
                        <div class="form-group">
                            <asp:Label ID="lblFirstName" runat="server" Text="FirstName:*" class="col-sm-4 control-label"></asp:Label>
                            <div class="col-sm-8">
                                 <asp:TextBox ID="txtFirstName" runat="server" class="form-control"></asp:TextBox>
                            </div>
                        </div>

                        <div class="form-group">
                            <asp:Label ID="lblLastName" runat="server" Text="Last Name:*" class="col-sm-4 control-label"></asp:Label>
                             <div class="col-sm-8">
                                <asp:TextBox ID="txtLastName" runat="server" class="form-control"></asp:TextBox>
                            </div>
                         </div>
                      
                          <div class="modal-footer">
                              <asp:Button ID="btnSave" type="button" runat="server" Text="Save"
                                  class="btn btn-primary" OnClick="btnSave_Click"/>
                             <asp:Button ID="btnCancel"  type="button" runat="server" Text="Cancel" class="btn btn-default" data-dismiss="modal" />

                        </div>
             
                 </div>

                </div> 
            </div> 
        </div>
              
           <!-- Add new user ends-->  

        </div>
    </section>
</asp:Content>