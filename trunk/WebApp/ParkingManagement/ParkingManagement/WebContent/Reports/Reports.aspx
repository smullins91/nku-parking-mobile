<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeBehind="Reports.aspx.cs" Inherits="ParkingManagement.WebContent.Reports.Reports" %>



<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <section class="featured">
         <div class="content-wrapper">
            
             <div style="margin-left: auto; margin-right: auto; text-align: center;">
                <h1>
               <asp:Label ID="Label1" runat="server" CssClass="label" Text="Parking Lot Reports" Width="100%"></asp:Label>
                </h1>
             </div>
             <p>
                         <asp:GridView ID="gvLots" runat="server" AutoGenerateColumns="False" HorizontalAlign="Center" Width="100%" DataKeyNames="lotNumber" CssClass="table table-hover table-striped" >
                                 <Columns>
                                 <asp:BoundField DataField="lotNumber" HeaderText="Lot Name" />
                                 <asp:BoundField DataField="typeString" HeaderText="Designation" />
                                 <asp:BoundField DataField="totalSpaces" HeaderText="Number of Spaces" />
                                 <asp:BoundField DataField="available" HeaderText="Available Spaces" />
                                 <asp:BoundField DataField="percentUsed" HeaderText="Percent Used" />

                             </Columns>
                         </asp:GridView>
                         <asp:SqlDataSource ID="SqlDataSource1" runat="server"></asp:SqlDataSource>
                         <asp:ObjectDataSource ID="AllLots" runat="server"></asp:ObjectDataSource>
                     <br />
                     <br />
             </p>
        </div>
    </section>
</asp:Content>