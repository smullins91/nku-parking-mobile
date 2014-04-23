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
                      <a href="AddUsers.aspx" class="btn" 
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
                              <asp:BoundField DataField="RoleId" HeaderText="RoleId"  ItemStyle-CssClass="hiddencol" HeaderStyle-CssClass="hiddencol"/>
                              <asp:BoundField DataField="Admin" HeaderText="Admin" />
                              <asp:ButtonField CommandName="editUser" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Edit" HeaderText="Edit User">
                                <ControlStyle CssClass="btn"></ControlStyle>
                              </asp:ButtonField>
                             <asp:ButtonField CommandName="deleteUser" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Delete" HeaderText="Delete User">
                                <ControlStyle CssClass="btn"></ControlStyle>
                                
                            </asp:ButtonField>
                             <asp:ButtonField CommandName="resetPassword" ControlStyle-CssClass="btn btn-info"
                                ButtonType="Button" Text="Reset Password" HeaderText="Reset Password">
                                <ControlStyle CssClass="btn"></ControlStyle>
                            </asp:ButtonField>
                         </Columns>

                     </asp:GridView>
                    
                 </ContentTemplate>
             </asp:UpdatePanel>
            <!-- GridView ends-->  

          
        <!-- Delete User starts here-->
            <div id="deleteModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true"
                 style="top:200px;left:30%" >
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="delModalLabel">Delete User</h3>
                </div>
                <asp:UpdatePanel ID="upDel" runat="server">
                    <ContentTemplate>
                        <div class="modal-body">
                            Are you sure you want to delete the record?
                            <asp:HiddenField ID="hfUserId" runat="server" />
                            <asp:HiddenField ID="hfIndex" runat="server" />
                        </div>
                        <div class="modal-footer">
                            <asp:Button ID="btnDelete" runat="server" Text="Delete" CssClass="btn" OnClick="btnDelete_Click" />
                            <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
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