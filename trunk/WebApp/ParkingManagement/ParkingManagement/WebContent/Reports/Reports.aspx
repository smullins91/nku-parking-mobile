<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeBehind="Reports.aspx.cs" Inherits="ParkingManagement.WebContent.Reports.Reports" %>

<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <section class="featured">
         <div class="content-wrapper">
           <h1>Reports</h1>
             <asp:Panel ID="Panel2" runat="server" Height="51px">
                 <asp:Button ID="Button1" runat="server" Text="Lot Usage" BackColor="Black" BorderColor="Black" ForeColor="White" Width="49%" />
                 <asp:Button ID="Button2" runat="server" BackColor="Black" BorderColor="Black" ForeColor="White" Text="User Report" Width="48%" />
             </asp:Panel>
             <p>
                 &nbsp;</p>
         <ul>
             <li>
             </li>
             <li>To include:<ul>
                 <li>Usage: Available space percentage of lot</li>
                 <li>User report: how long user has been parked in a particular spot</li>
                 </ul>
             </li>
             <li>
                 <asp:Panel ID="Panel1" runat="server">
                 </asp:Panel>
             </li>
         </ul>
        </div>
    </section>
</asp:Content>