<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeBehind="Reports.aspx.cs" Inherits="ParkingManagement.WebContent.Reports.Reports" %>

<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <section class="featured">
         <div class="content-wrapper">
           <h1>Reports</h1>
             <asp:Panel ID="Panel2" runat="server" Height="51px">
                 <asp:Button ID="Button1" runat="server" Text="Lot Usage" BackColor="Black" BorderColor="Black" ForeColor="White" Width="49%" OnClick="Button1_Click" />
                 <asp:Button ID="Button2" runat="server" BackColor="Black" BorderColor="Black" ForeColor="White" Text="User Report" Width="48%" OnClick="Button2_Click" />
             </asp:Panel>
             <p>
                 <asp:MultiView ID="MultiView1" runat="server" ActiveViewIndex="1">
                     <asp:View ID="View1" runat="server">
                         <asp:GridView ID="gvLots" runat="server" AutoGenerateColumns="False" DataSourceID="AllLots" Height="140px" Width="827px">
                             <Columns>
                                 <asp:BoundField HeaderText="Lot Name" />
                                 <asp:BoundField HeaderText="Designation" />
                                 <asp:BoundField HeaderText="Usage" />
                             </Columns>
                         </asp:GridView>
                         <asp:SqlDataSource ID="SqlDataSource1" runat="server"></asp:SqlDataSource>
                         <asp:ObjectDataSource ID="AllLots" runat="server"></asp:ObjectDataSource>
                     </asp:View>
                     <asp:View ID="View2" runat="server">
                         <asp:GridView ID="gvUserStatus" runat="server" AutoGenerateColumns="False" Height="192px" Width="803px">
                             <Columns>
                                 <asp:BoundField HeaderText="User Name" />
                                 <asp:BoundField HeaderText="User ID" />
                                 <asp:BoundField HeaderText="Access" />
                                 <asp:BoundField HeaderText="Lot Used" />
                                 <asp:BoundField HeaderText="Parked or Reserved" />
                                 <asp:BoundField HeaderText="Duration" />
                             </Columns>
                         </asp:GridView>
                     </asp:View>
                     <br />
                     <br />
                 </asp:MultiView>
             </p>
        </div>
    </section>
</asp:Content>