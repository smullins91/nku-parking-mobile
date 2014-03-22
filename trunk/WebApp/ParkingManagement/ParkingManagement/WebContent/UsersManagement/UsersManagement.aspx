<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeBehind="UsersManagement.aspx.cs" Inherits="ParkingManagement.WebContent.UsersManagement.UsersManagement" %>

<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <link href="../../Content/bootstrap2.2.css" rel="stylesheet" />
     <section class="featured">
         <div class="content-wrapper">
             <div style="margin-bottom:10px; height:50px; margin-top:10px"">
                 <div style="float:left">
                     <h4>Users Management</h4>
                 </div>
                  <div style="float:right; margin-top:20px" >
                      <a data-toggle="modal" href="#addModal" class="btn btn-info" 
                     style="margin-right:20px; color:white; font-weight:bold; margin-top:15px;padding:10px">Add New User</a>
                     
                  </div>
                 
             </div>

         <!-- GridView begins-->  
             <asp:UpdatePanel ID="upUsers" runat="server">
                 <ContentTemplate>
                     <asp:GridView ID="gvUsers" runat="server" HorizontalAlign="Center" Width="100%" AutoGenerateColumns="false" DataKeyNames="UserId"
                          AllowPaging="true" CssClass="table table-hover table-striped" OnRowCommand="gvUsers_RowCommand" PageSize="5" OnPageIndexChanging="gvUsers_PageIndexChanging"
                          >
                         <Columns>
                              <asp:BoundField DataField="UserName" HeaderText="UserName" />
                              <asp:BoundField DataField="LastName" HeaderText="Last Name" />
                              <asp:BoundField DataField="FirstName" HeaderText="First Name" />
                              <asp:BoundField DataField="Email" HeaderText="Email" />
                              <asp:BoundField DataField="Role" HeaderText="Role" />
                              <asp:ButtonField CommandName="editUser" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Edit" HeaderText="Edit User">
                                <ControlStyle CssClass="btn btn-info"></ControlStyle>
                              </asp:ButtonField>
                             <asp:ButtonField CommandName="deleteUser" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Delete" HeaderText="Delete User">
                                <ControlStyle CssClass="btn btn-info"></ControlStyle>
                                
                            </asp:ButtonField>
                             <asp:ButtonField CommandName="resetPassword" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Reset Password" HeaderText="Reset Password">
                                <ControlStyle CssClass="btn btn-info"></ControlStyle>
                            </asp:ButtonField>
                         </Columns>

                     </asp:GridView>

                 </ContentTemplate>
             </asp:UpdatePanel>
            <!-- GridView ends-->  

           <!-- Add new user begins--> 
        <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
            >

         <div class="modal-dialog" style="margin-top:0">
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
                                  class="btn btn-info" OnClick="btnSave_Click" />
                             <asp:Button ID="btnCancel"  type="button" runat="server" Text="Cancel" class="btn btn-info" data-dismiss="modal" />

                        </div>
             
                 </div>

                </div> 
            </div> 
        </div>
          <!-- Add new user ends-->  

        <!-- Delete User starts here-->
            <div id="deleteModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="delModalLabel">Delete Record</h3>
                </div>
                <asp:UpdatePanel ID="upDel" runat="server">
                    <ContentTemplate>
                        <div class="modal-body">
                            Are you sure you want to delete the record?
                            <asp:HiddenField ID="hfCode" runat="server" />
                        </div>
                        <div class="modal-footer">
                            <asp:Button ID="btnDelete" runat="server" Text="Delete" CssClass="btn btn-info" OnClick="btnDelete_Click" />
                            <button class="btn btn-info" data-dismiss="modal" aria-hidden="true">Cancel</button>
                        </div>
                    </ContentTemplate>
                    <Triggers>
                        <asp:AsyncPostBackTrigger ControlID="btnDelete" EventName="Click" />
                    </Triggers>
                </asp:UpdatePanel>
            </div>
         <!-- Delete User ends here-->     
        
                 </div>
    </section>
  
</asp:Content>